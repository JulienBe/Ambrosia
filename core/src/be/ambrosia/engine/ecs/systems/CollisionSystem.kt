package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.ColliderComp
import be.ambrosia.engine.ecs.components.DirComp
import be.ambrosia.engine.ecs.components.PosComp
import be.ambrosia.engine.g.GBench
import be.ambrosia.engine.g.collisions.GSide
import be.ambrosia.engine.map.MapTile
import be.ambrosia.engine.map.globalelems.Wall
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import ktx.ashley.allOf
import ktx.ashley.has

class CollisionSystem : IteratingSystem(family) {

    override fun processEntity(entity: Entity, delta: Float) {
//        bench.begin()
        val pos = posMapper.get(entity)
        val rect = getRect(pos, rect)
        val collider = colliderMapper.get(entity)
        pos.tileSet
                .onEach { tile ->
                    tile.getElements().forEach {
                        if (collider.collidingWithTiles and it.id != 0)
                            collider.collidingTile(entity, tile, it)
                    }
//                    tile.entities.remove(entity)
                }
                .flatMap { it.entities }
                .filter { isColliding(entity, collider, rect, it, colliderMapper.get(it)) }
                .distinct()
                .forEach { other ->
                    val otherCollider = colliderMapper[other]
                    val otherPos = posMapper[other]
                    otherCollider.collidesWith(other, entity)
                    collider.collidesWith(entity, other)
                    // TODO check those
                    if (collider.pushBack)
                        pushBack(pos, otherPos, pushBackStrength)
                    if (otherCollider.pushBack)
                        pushBack(otherPos, pos, pushBackStrength)
                    if (collider.pushBounce)
                        pushBounce(entity, other)
                    if (otherCollider.pushBounce)
                        pushBounce(other, entity)
                }
//        bench.end()
    }

    private fun isColliding(me: Entity, collider: ColliderComp, rect: Rectangle, other: Entity, otherCollider: ColliderComp): Boolean {
        return me != other &&
                rect.overlaps(getRect(posMapper.get(other), otherCollider.rectangle)) &&
                (collider.collidingWith and otherCollider.id != 0 || otherCollider.collidingWith and collider.id != 0)
    }

    private fun getRect(p: PosComp, r: Rectangle): Rectangle {
        r.set(p.x, p.y, p.w, p.h)
        return r
    }

    companion object {
        val bench = GBench("coll sys")
        val posMapper = PosComp.mapper
        val colliderMapper = ColliderComp.mapper
        val rect = Rectangle()
        private val v2 = Vector2()
        const val pushBackStrength = 0.1f

        val family: Family = allOf(
                PosComp::class,
                ColliderComp::class).get()

        fun pushBounce(me: Entity, other: Entity) {
//            if (other.has(DirComp.mapper) && me.has(DirComp.mapper)) {
//                val otherDir = DirComp.mapper.get(other)
//                DirComp.mapper.get(me).setDir(otherDir.previousDir.x, otherDir.previousDir.y)
//            }
        }

        fun pushBack(mePos: PosComp, otherPos: PosComp, pushBackStrength: Float) {
            v2.set(mePos.centerX - otherPos.centerX, mePos.centerY - otherPos.centerY)
                .setLength((mePos.w + mePos.h / v2.len()) * pushBackStrength)
            mePos.x += v2.x
            mePos.y += v2.y
        }

        fun goTowards(mePos: PosComp, otherX: Float, otherY: Float, meDir: DirComp, strenght: Float) {
            v2.set(mePos.x - otherX, mePos.y - otherY)
            v2.setLength((mePos.w + mePos.h / v2.len()))
            v2.scl(strenght)
            meDir.addX(v2.x)
            meDir.addY(v2.y)
        }

        fun wallCollision(pos: PosComp, dir: DirComp, wall: Wall, tile: MapTile) {
            if (wall.exposedSide.bounceV.x != 0f)
                dir.setDir(Math.abs(dir.dirX) * wall.exposedSide.bounceV.x, dir.dirY)
            if (wall.exposedSide.bounceV.y != 0f)
                dir.setDir(dir.dirX, Math.abs(dir.dirY) * wall.exposedSide.bounceV.y)
            when (wall.exposedSide) {
                GSide.LEFT -> {
                    dir.setDir(Math.abs(dir.dirX) * wall.exposedSide.bounceV.x, dir.dirY)
                    pos.x = tile.worldX - pos.w
                }
                GSide.RIGHT -> {
                    dir.setDir(Math.abs(dir.dirX) * wall.exposedSide.bounceV.x, dir.dirY)
                    pos.x = tile.worldRight
                }
                GSide.TOP -> {
                    dir.setDir(dir.dirX, Math.abs(dir.dirY) * wall.exposedSide.bounceV.y)
                    pos.y = tile.worldUp
                }
                GSide.BOTTOM -> {
                    dir.setDir(dir.dirX, Math.abs(dir.dirY) * wall.exposedSide.bounceV.y)
                    pos.y = tile.worldY - pos.h
                }
            }
        }

        fun wallCollideAndStick(pos: PosComp, wall: Wall, dir: DirComp) {
            when (wall.exposedSide) {
                GSide.LEFT ->   {
                    pos.x = (wall.tile.worldX - pos.w) - 0.01f
                    dir.setDirAndKeepSpeed(0f, dir.dirY)
                }
                GSide.RIGHT ->  {
                    pos.x = wall.tile.worldRight + 0.01f
                    dir.setDirAndKeepSpeed(0f, dir.dirY)
                }
                GSide.TOP ->    {
                    pos.y = wall.tile.worldUp + 0.01f
                    dir.setDirAndKeepSpeed(dir.dirX, 0f)
                }
                GSide.BOTTOM -> {
                    pos.y = (wall.tile.worldY - pos.h) - 0.01f
                    dir.setDirAndKeepSpeed(dir.dirX, 0f)
                }
            }

        }
        private fun actOnSide(side: GSide, dir: DirComp): GSide {
            when (side) {
                GSide.BOTTOM -> dir.setDir(dir.dirX, -Math.abs(dir.dirY))
                GSide.TOP -> dir.setDir(dir.dirX, Math.abs(dir.dirY))
                GSide.LEFT -> dir.setDir(-Math.abs(dir.dirX), dir.dirY)
                GSide.RIGHT -> dir.setDir(Math.abs(dir.dirX), dir.dirY)
            }
            return side
        }

    }
}

package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.g.GSide
import be.ambrosia.engine.map.GameMap
import be.ambrosia.engine.map.MapTile
import be.ambrosia.engine.map.elements.Wall
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import ktx.ashley.allOf
import ktx.collections.GdxSet
import ktx.collections.contains

class CollisionSystem : IteratingSystem(family) {

    val tileSet = GdxSet<MapTile>()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val pos = posMapper.get(entity)
        val dim = dimMapper.get(entity)
        val rect = getRect(pos, dimMapper.get(entity), rect)
        val entities = ECSEngine.getEntitiesFor(family)
        val collider = colliderMapper.get(entity)
        val nextIndex = entities.indexOf(entity, true) + 1
        for (i in nextIndex until entities.size()) {
            val other = entities[i]
            if (rect.overlaps(getRect(posMapper.get(other), dimMapper.get(other), colliderMapper.get(other).rectangle))) {
                colliderMapper.get(other).collidesWith(other, entity)
                collider.collidesWith(entity, other)
                if (collider.pushBack)
                    pushBack(pos,                   posMapper.get(other).x, posMapper.get(other).y, dim)
                    pushBack(posMapper.get(other),  pos.x,                  pos.y,                  dimMapper.get(other))
                if (collider.pushBounce) {
                    pushBounce(entity, other)
                    pushBounce(other, entity)
                }
            }

        }
        tileSet.clear()
        tileSet.add(GameMap.getTileInWorldCoord(pos.x,          pos.y))
        tileSet.add(GameMap.getTileInWorldCoord(pos.x + dim.w,  pos.y))
        tileSet.add(GameMap.getTileInWorldCoord(pos.x + dim.w,  pos.y + dim.h))
        tileSet.add(GameMap.getTileInWorldCoord(pos.x,          pos.y + dim.h))
        tileSet.forEach { tile ->
            tile.elements.forEach {
                if (collider.tileElementColliding.contains(it.index))
                    collider.collidingTile(entity, tile, it)
            }
        }
    }

    private fun getRect(p: PosComp, d: DimComp, r: Rectangle): Rectangle {
        r.set(p.x, p.y, d.w, d.h)
        return r
    }

    companion object {
        val posMapper = PosComp.mapper
        val dimMapper = DimComp.mapper
        val colliderMapper = ColliderComp.mapper
        val rect = Rectangle()
        val v2 = Vector2()
        val pushBackStrength = 0.1f

        val family: Family = allOf(
                PosComp::class,
                DimComp::class,
                ColliderComp::class).get()

        fun pushBounce(me: Entity, other: Entity) {
            val meDir = DirComp.mapper.get(me)
            val otherDir = DirComp.mapper.get(other)
            meDir.setDir(otherDir.previousDir.x, otherDir.previousDir.y)
        }

        fun pushBack(mePos: PosComp, otherX: Float, otherY: Float, meDim: DimComp) {
            val dep = v2.set(mePos.x - otherX, mePos.y - otherY)
            dep.setLength(meDim.w + meDim.h / dep.len())
            dep.scl(pushBackStrength)
            mePos.x += dep.x
            mePos.y += dep.y
        }

        fun wallCollision(pos: PosComp, dir: DirComp, dim: DimComp, wall: Wall, tile: MapTile) {
            if (wall.exposedSide.bounceV.x != 0f)
                dir.setDir(Math.abs(dir.dirX) * wall.exposedSide.bounceV.x, dir.dirY)
            if (wall.exposedSide.bounceV.y != 0f)
                dir.setDir(dir.dirX, Math.abs(dir.dirY) * wall.exposedSide.bounceV.y)
//            pushBack(pos, pos.x - wall.exposedSide.bounceV.x, pos.y - wall.exposedSide.bounceV.y, dim)
            when (wall.exposedSide) {
                GSide.LEFT -> {
                    dir.setDir(Math.abs(dir.dirX) * wall.exposedSide.bounceV.x, dir.dirY)
                    pos.x = tile.worldX - dim.w
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
                    pos.y = tile.worldY - dim.h
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

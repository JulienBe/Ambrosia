package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.g.GSide
import be.ambrosia.engine.map.GameMap
import be.ambrosia.engine.map.elements.Wall
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import ktx.ashley.allOf
import ktx.collections.contains

class CollisionSystem : IteratingSystem(family) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val pos = posMapper.get(entity)
        val rect = getRect(pos, dimMapper.get(entity), rect)
        val entities = ECSEngine.getEntitiesFor(family)
        val collider = colliderMapper.get(entity)
        entities.forEach {
            if (it != entity && rect.overlaps(getRect(posMapper.get(it), dimMapper.get(it), colliderMapper.get(it).rectangle))) {
                colliderMapper.get(it).collidesWith(it, entity)
                collider.collidesWith(entity, it)
                if (collider.pushBack)
                    pushBack(pos, posMapper.get(it).x, posMapper.get(it).y, dimMapper.get(entity))
                if (collider.pushBounce)
                    pushBounce(entity, it)
            }
        }
        if (pos.x > 0 && pos.y > 0) {
            val tile = GameMap.getTileInWorldCoord(pos.x, pos.y)
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
            val otherDir = DirComp.mapper.get(other).previousDir
            meDir.setDir(otherDir.x, otherDir.y)
        }

        fun pushBack(mePos: PosComp, otherX: Float, otherY: Float, meDim: DimComp) {
            val dep = v2.set(mePos.x - otherX, mePos.y - otherY)
            dep.setLength(meDim.w + meDim.h / dep.len())
            dep.scl(pushBackStrength)
            mePos.x += dep.x
            mePos.y += dep.y
        }

        fun wallCollision(pos: PosComp, dir: DirComp, dim: DimComp, wall: Wall) {
            if (wall.exposedSide.bounceV.x != 0f)
                dir.setDir(Math.abs(dir.dirX) * wall.exposedSide.bounceV.x, dir.dirY)
            if (wall.exposedSide.bounceV.y != 0f)
                dir.setDir(dir.dirX, Math.abs(dir.dirY) * wall.exposedSide.bounceV.y)
            pushBack(pos, pos.x - wall.exposedSide.bounceV.x, pos.y - wall.exposedSide.bounceV.y, dim)
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

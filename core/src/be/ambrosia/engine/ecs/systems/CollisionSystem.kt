package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import ktx.ashley.allOf

class CollisionSystem : IteratingSystem(family) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val rect = getRect(posMapper.get(entity), dimMapper.get(entity), rect)
        val entities = ECSEngine.getEntitiesFor(family)
        val collider = colliderMapper.get(entity)
        entities.forEach {
            if (it != entity && rect.overlaps(getRect(posMapper.get(it), dimMapper.get(it), colliderMapper.get(it).rectangle))) {
                colliderMapper.get(it).collidesWith(it, entity)
                collider.collidesWith(entity, it)
                if (collider.pushBack)
                    pushBack(entity, it)
                if (collider.pushBounce)
                    pushBounce(entity, it)
            }
        }
    }

    private fun pushBounce(me: Entity, other: Entity) {
        val meDir = DirComp.mapper.get(me)
        val otherDir = DirComp.mapper.get(other).previousDir
        meDir.setDir(otherDir.x, otherDir.y)
    }

    private fun pushBack(me: Entity, other: Entity) {
        val mePos = posMapper.get(me)
        val otherPos = posMapper.get(other)
        val meDim = dimMapper.get(me)
        val dep = v2.set(mePos.x - otherPos.x, mePos.y - otherPos.y)
        dep.setLength(meDim.w + meDim.h / dep.len())
        dep.scl(pushBackStrength)
        mePos.x += dep.x
        mePos.y += dep.y
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
    }
}
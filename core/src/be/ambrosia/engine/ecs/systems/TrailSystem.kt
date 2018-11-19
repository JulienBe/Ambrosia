package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf

class TrailSystem : IteratingSystem(family) {

    override fun processEntity(entity: Entity, delta: Float) {
        val trailComp = TrailComp.mapper[entity] ?: return
        val trail = ECSEngine.createEntity()
        val trailPos = ECSEngine.createComponent(PosComp::class.java, trail)
        val trailDraw = ECSEngine.createComponent(Drawable2DComp::class.java, trail)
        val trailHp = ECSEngine.createComponent(HPComp::class.java, trail)
        val trailAct = ECSEngine.createComponent(ActComp::class.java, trail)
        val pos = PosComp.mapper[entity]

        trailPos.x = pos.x
        trailPos.y = pos.y
        trailPos.w = pos.w
        trailPos.h = pos.h
        trailDraw.draw = {
            trailComp.draw.invoke(it, trail)
        }
        trailHp.hp = trailComp.lifetime
        trailAct.onAct = {
            if (trailHp.hp-- < 0)
                ECSEngine.removeEntity(trail)
        }

        ECSEngine.addEntity(trail)
    }

    companion object {
        val family: Family = allOf(TrailComp::class, PosComp::class, Drawable2DComp::class).get()
    }
}
package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.ActComp
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf

class ActSystem : IteratingSystem(family) {

    override fun processEntity(entity: Entity, delta: Float) {
        ActComp.mapper.get(entity).onAct.invoke(entity)
    }

    companion object {
        val family: Family = allOf(ActComp::class).get()
    }
}
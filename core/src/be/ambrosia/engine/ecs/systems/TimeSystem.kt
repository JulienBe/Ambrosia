package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.TimeComp
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf

class TimeSystem : IteratingSystem(family) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val e = time.get(entity)
        e.delta = deltaTime
        e.total += e.delta
    }

    companion object {
        val family: Family = allOf(TimeComp::class).get()
        val time = TimeComp.mapper
    }
}
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
        e.timers.forEach {
            it.value.current += e.delta
            if (it.value.current > it.value.nextTrigger)
                it.value.onTrigger.invoke(entity)
        }
    }

    companion object {
        val family: Family = allOf(TimeComp::class).get()
        val time = TimeComp.mapper
    }
}
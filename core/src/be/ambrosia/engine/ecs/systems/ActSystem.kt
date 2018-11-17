package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.ActComp
import be.ambrosia.engine.ecs.components.Drawable2DComp
import be.ambrosia.engine.ecs.components.PosComp
import be.ambrosia.engine.g.GBench
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.badlogic.ashley.systems.SortedIteratingSystem
import ktx.ashley.allOf
import java.util.*

class ActSystem : IntervalIteratingSystem(family, 0.15f) {

    override fun processEntity(entity: Entity) {
        ActComp.mapper.get(entity).onAct.invoke(entity)
    }

    companion object {
        val family: Family = allOf(ActComp::class).get()
    }
}
package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.ecs.components.Drawable2DComp
import be.ambrosia.engine.ecs.components.PosComp
import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.g.GBench
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import ktx.ashley.allOf
import java.util.*

class Drawing2DSystem : SortedIteratingSystem(family, ZComparator()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
//        bench.begin()
        drawable.get(entity).draw.invoke(batch)
//        bench.end()
    }

    private class ZComparator : Comparator<Entity> {
        private val pm = PosComp.mapper

        override fun compare(e1: Entity, e2: Entity): Int {
            return Math.signum((pm.get(e1).z - pm.get(e2).z).toDouble()).toInt()
        }
    }

    companion object {
        val bench = GBench("drawing 2D")
        val batch = AmbContext.cxt.inject<GBatch>()
        val family: Family = allOf(Drawable2DComp::class).get()

        val drawable = Drawable2DComp.mapper
    }
}
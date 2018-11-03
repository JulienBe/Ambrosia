package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.DimComp
import be.ambrosia.engine.ecs.components.Drawable2DComp
import be.ambrosia.engine.ecs.components.PosComp
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import ktx.ashley.allOf
import java.util.*


class Drawing2DSystem : SortedIteratingSystem(family, ZComparator()) {


    override fun processEntity(entity: Entity, deltaTime: Float) {
        val pos = pos.get(entity)
        val dim = dim.get(entity)
        val d = drawable.get(entity)
        d.batch.setColor(d.color)
        d.batch.draw(d.tr, pos.x, pos.y, dim.w, dim.h)
    }

    private class ZComparator : Comparator<Entity> {
        private val pm = PosComp.mapper

        override fun compare(e1: Entity, e2: Entity): Int {
            return Math.signum((pm.get(e1).z - pm.get(e2).z).toDouble()).toInt()
        }
    }

    companion object {
        val family: Family = allOf(PosComp::class, DimComp::class, Drawable2DComp::class).get()

        val pos = PosComp.mapper
        val dim = DimComp.mapper
        val drawable = Drawable2DComp.mapper
    }
}
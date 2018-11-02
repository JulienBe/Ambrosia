package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.DimComp
import be.ambrosia.engine.ecs.components.Drawable2DComp
import be.ambrosia.engine.ecs.components.Drawable3DComp
import be.ambrosia.engine.ecs.components.PosComp
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf

class Drawing2DSystem : IteratingSystem(family) {

    val pos = PosComp.mapper
    val dim = DimComp.mapper
    val drawable = Drawable2DComp.mapper

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val pos = pos.get(entity)
        val dim = dim.get(entity)
        val d = drawable.get(entity)
        d.batch.setColor(d.color)
        d.batch.draw(d.tr, pos.x, pos.y, dim.w, dim.h)
    }

    companion object {
        val family: Family = allOf(PosComp::class, DimComp::class, Drawable2DComp::class).get()
    }
}
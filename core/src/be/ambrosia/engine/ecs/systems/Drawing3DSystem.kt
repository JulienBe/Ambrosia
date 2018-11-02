package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.DimComp
import be.ambrosia.engine.ecs.components.Drawable2DComp
import be.ambrosia.engine.ecs.components.Drawable3DComp
import be.ambrosia.engine.ecs.components.PosComp
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf

class Drawing3DSystem : IteratingSystem(family) {

    val pos = PosComp.mapper
    val dim = DimComp.mapper
    val drawable = Drawable3DComp.mapper

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val pos = pos.get(entity)
        val dim = dim.get(entity)
        val d = drawable.get(entity)
        d.instance.transform.setToTranslation(pos.x, pos.y, 0f)
        d.batch.render(d.instance)
    }

    companion object {
        val family: Family = allOf(PosComp::class, DimComp::class, Drawable3DComp::class).get()
    }
}
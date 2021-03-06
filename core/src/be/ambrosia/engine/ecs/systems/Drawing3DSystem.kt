package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.Drawable3DComp
import be.ambrosia.engine.ecs.components.PosComp
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf

class Drawing3DSystem : IteratingSystem(family) {


    override fun processEntity(entity: Entity, deltaTime: Float) {
        val pos = pos.get(entity)
        val d = drawable.get(entity)
        d.instance.transform.setToTranslation(pos.x, pos.y, 0f)
        d.batch.render(d.instance)
    }

    companion object {
        val family: Family = allOf(PosComp::class, Drawable3DComp::class).get()

        val pos = PosComp.mapper
        val drawable = Drawable3DComp.mapper
    }
}
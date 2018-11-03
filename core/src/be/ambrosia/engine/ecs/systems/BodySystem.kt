package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.*
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf

class BodySystem : IteratingSystem(family) {


    override fun processEntity(entity: Entity, deltaTime: Float) {
        val p = posMapper.get(entity)
        val b = dirMapper.get(entity)
//        val d = dimMapper.get(entity)
        b.body.setTransform(p.x, p.y, 0f)
    }

    companion object {
        val posMapper = PosComp.mapper
        val dirMapper = BodyComp.mapper
        val dimMapper = DimComp.mapper

        val family: Family = allOf(
                PosComp::class,
                DimComp::class,
                BodyComp::class).get()
    }
}
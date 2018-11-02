package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.DirComp
import be.ambrosia.engine.ecs.components.PosComp
import be.ambrosia.engine.ecs.components.TimeComp
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf

class MovementSystem : IteratingSystem(family) {

    val posMapper = PosComp.mapper
    val dirMapper = DirComp.mapper
    val timeMapper = TimeComp.mapper

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val pos = posMapper.get(entity)
        val dir = dirMapper.get(entity)
        val time = timeMapper.get(entity)
        pos.x += dir.dir.x * time.delta
        pos.y += dir.dir.y * time.delta
    }

    companion object {
        val family: Family = allOf(
                PosComp::class,
                DirComp::class,
                TimeComp::class).get()
    }
}
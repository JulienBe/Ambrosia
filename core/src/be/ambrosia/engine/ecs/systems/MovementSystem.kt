package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.DirComp
import be.ambrosia.engine.ecs.components.PosComp
import be.ambrosia.engine.ecs.components.TimeComp
import be.ambrosia.engine.g.GBench
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf

class MovementSystem : IteratingSystem(family) {


    override fun processEntity(entity: Entity, deltaTime: Float) {
//        bench.begin()
        val pos = posMapper.get(entity)
        val dir = dirMapper.get(entity)
        val time = timeMapper.get(entity)
        pos.x += dir.dirX * time.delta
        pos.y += dir.dirY * time.delta
        dir.clampSpeed()
        dir.validate()
//        bench.end()
    }

    companion object {
        val bench = GBench("movement ")
        val posMapper = PosComp.mapper
        val dirMapper = DirComp.mapper
        val timeMapper = TimeComp.mapper

        val family: Family = allOf(
                PosComp::class,
                DirComp::class,
                TimeComp::class).get()
    }
}
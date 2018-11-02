package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.ecs.components.DirComp
import be.ambrosia.engine.ecs.components.PosComp
import be.ambrosia.engine.ecs.components.TimeComp
import be.ambrosia.engine.ecs.components.WandererComp
import be.ambrosia.engine.g.GRand
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf

class WandererSystem : IteratingSystem(family) {

    val wandererMapper = WandererComp.mapper
    val dirMapper = DirComp.mapper
    val timeMapper = TimeComp.mapper

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val wanderer = wandererMapper.get(entity)
        val dir = dirMapper.get(entity)
        val time = timeMapper.get(entity)
        if (wanderer.nextPush < time.total) {
            wanderer.nextPush = time.total + wanderer.pushRate
            dir.dir.x += r.gauss(wanderer.amplitude)
            dir.dir.y += r.gauss(wanderer.amplitude)
            dir.dir.clamp(dir.minSpeed, dir.maxSpeed)
        }
    }

    companion object {
        val family: Family = allOf(
                PosComp::class,
                WandererComp::class,
                TimeComp::class).get()
        val r = AmbContext.cxt.inject<GRand>()
    }

}
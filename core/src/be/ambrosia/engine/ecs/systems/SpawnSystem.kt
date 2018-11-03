package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.TimeComp
import be.ambrosia.engine.ecs.components.TimedSpawnComp
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf

class SpawnSystem : IteratingSystem(family) {


    override fun processEntity(entity: Entity, deltaTime: Float) {
        val spawn = timedSpawnMapper.get(entity)
        val time = timeMapper.get(entity)
        if (spawn.nextSpawn < time.total) {
            spawn.nextSpawn = time.total + spawn.nextSpawnDelay.invoke()
            ECSEngine.addEntity(spawn.spawn.invoke())
        }
    }

    companion object {
        val timedSpawnMapper = TimedSpawnComp.mapper
        val timeMapper = TimeComp.mapper

        val family: Family = allOf(
                TimedSpawnComp::class,
                TimeComp::class).get()
    }

}
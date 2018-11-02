package be.ambrosia.getlost.templates

import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import com.badlogic.ashley.core.Entity

object CyclopSpwaner {

    fun init(entity: Entity): Entity {
        val spawner = ECSEngine.createComponent(TimedSpawnComp::class.java)
        val time = ECSEngine.createComponent(TimeComp::class.java)
        spawner.spawn = {spawn()}
        entity.add(spawner).add(time)
        return entity
    }

    fun spawn(): Entity {
        val entity = Cyclop.init(ECSEngine.createEntity())
        return entity
    }

}
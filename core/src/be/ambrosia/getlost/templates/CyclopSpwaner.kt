package be.ambrosia.getlost.templates

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.Dimensions
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.ecs.systems.WandererSystem
import be.ambrosia.engine.g.GRand
import com.badlogic.ashley.core.Entity

object CyclopSpwaner {

    val rand = AmbContext.cxt.inject<GRand>()

    fun init(entity: Entity): Entity {
        val spawner = ECSEngine.createComponent(TimedSpawnComp::class.java)
        val time = ECSEngine.createComponent(TimeComp::class.java)
        spawner.spawn = { spawn() }
        spawner.nextSpawnDelay = { determineNextSpawn() }
        entity.add(spawner).add(time)
        return entity
    }

    private fun determineNextSpawn(): Float {
//        return 1f
        return ECSEngine.getEntitiesFor(WandererSystem.family).size().toFloat()
    }

    private fun spawn(): Entity {
        val entity = Cyclop.init(ECSEngine.createEntity(), rand.float(Dimensions.gameWidth), rand.float(Dimensions.gameHeight))
        return entity
    }

}
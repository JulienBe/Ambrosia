package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.g.GRand
import com.badlogic.ashley.core.Entity
import ktx.ashley.mapperFor

class TimedSpawnComp : TemplateComp {

    lateinit var spawn: () -> Entity
    var nextSpawnDelay = {rand.float(1f, 3f)}
    var nextSpawn = nextSpawnDelay.invoke()

    override fun reset() {
        super.reset()
        nextSpawn = 0f
        nextSpawnDelay = {rand.float(1f, 3f)}
    }

    companion object {
        val mapper = mapperFor<TimedSpawnComp>()
        val rand = AmbContext.cxt.inject<GRand>()
    }
}
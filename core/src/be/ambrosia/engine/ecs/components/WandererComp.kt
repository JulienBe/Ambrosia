package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.g.GRand
import ktx.ashley.mapperFor

class WandererComp : TemplateComp {

    var pushRate = rand.float(1f, 3f)
    var nextPush = pushRate
    var amplitude = rand.float(1f, 2f)

    companion object {
        val mapper = mapperFor<WandererComp>()
        val rand = AmbContext.cxt.inject<GRand>()
    }
}
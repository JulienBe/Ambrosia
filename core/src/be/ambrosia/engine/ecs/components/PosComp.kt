package be.ambrosia.engine.ecs.components

import ktx.ashley.mapperFor

class PosComp(var x: Float = 0f, var y: Float = 0f) : TemplateComp {
    companion object {
        val mapper = mapperFor<PosComp>()
    }
}
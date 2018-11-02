package be.ambrosia.engine.ecs.components

import ktx.ashley.mapperFor

class ParticleComp : TemplateComp {
    companion object {
        val mapper = mapperFor<ParticleComp>()
    }
}
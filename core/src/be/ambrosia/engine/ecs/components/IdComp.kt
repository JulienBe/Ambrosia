package be.ambrosia.engine.ecs.components

import ktx.ashley.mapperFor

class IdComp(var id: Int = -1) : TemplateComp {

    companion object {
        val mapper = mapperFor<IdComp>()
    }
}
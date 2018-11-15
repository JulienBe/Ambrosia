package be.ambrosia.engine.ecs.components

import ktx.ashley.mapperFor

class CanBeSensedComp : TemplateComp {
    var id = 0
    companion object {
        val mapper = mapperFor<CanBeSensedComp>()
    }
}
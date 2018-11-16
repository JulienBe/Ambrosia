package be.ambrosia.getlost.components

import be.ambrosia.engine.ecs.components.TemplateComp
import ktx.ashley.mapperFor

class EnergyConsummerComp : TemplateComp {

    var energy = 0

    companion object {
        val mapper = mapperFor<EnergyConsummerComp>()
    }
}
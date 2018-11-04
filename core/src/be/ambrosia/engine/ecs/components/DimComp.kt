package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.Dimensions
import ktx.ashley.mapperFor

class DimComp(var w: Float = 0f, var h: Float = 0f) : TemplateComp {

    var hw = w / 2f
    var hh = h / 2f

    fun set(w: Float, h: Float) {
        this.w = w * Dimensions.pixel
        this.h = h * Dimensions.pixel
        hw = w / 2f
        hh = h / 2f
    }

    companion object {
        val mapper = mapperFor<DimComp>()
    }
}
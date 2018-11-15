package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.Dimensions
import be.ambrosia.engine.map.MapTile
import ktx.ashley.mapperFor
import ktx.collections.GdxSet

class PosComp(var x: Float = 0f, var y: Float = 0f, var z: Float = 0f, var w: Float = 0f, var h: Float = 0f) : TemplateComp {

    val tileSet = GdxSet<MapTile>()
    var hw = w / 2f
    var hh = h / 2f

    fun set(w: Float, h: Float) {
        this.w = w * Dimensions.pixel
        this.h = h * Dimensions.pixel
        hw = w / 2f
        hh = h / 2f
    }


    companion object {
        val mapper = mapperFor<PosComp>()
    }
}
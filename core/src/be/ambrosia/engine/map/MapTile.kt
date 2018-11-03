package be.ambrosia.engine.map

import be.ambrosia.engine.g.GBatch
import com.badlogic.gdx.utils.Pool
import ktx.collections.GdxArray

class MapTile private constructor(){
    var elements = GdxArray<MapElement>()

    fun draw(b: GBatch, x: Int, y: Int) {
        elements.forEach {
            it.draw(b, x, y)
        }
    }

    companion object {
        val pool = object : Pool<MapTile>() {
            override fun newObject(): MapTile {
                return MapTile()
            }
        }
    }
}
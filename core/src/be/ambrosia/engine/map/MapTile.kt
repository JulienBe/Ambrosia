package be.ambrosia.engine.map

import be.ambrosia.engine.g.GBatch
import com.badlogic.gdx.utils.Pool
import ktx.collections.GdxArray

class MapTile private constructor() {
    var elements = GdxArray<MapElement>()
    var x = 0
    var y = 0
    var worldX = 0f
    var worldY = 0f
    var worldCenterX = worldX + GameMap.tileHalfSize
    var worldCenterY = worldY + GameMap.tileHalfSize
    var worldRight = worldX + GameMap.tileSize
    var worldUp = worldY + GameMap.tileSize


    fun set(x: Int, y: Int) {
        this.x = x
        this.y = y
        worldX = x * GameMap.tileSize
        worldY = y * GameMap.tileSize
    }

    fun draw(b: GBatch) {
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
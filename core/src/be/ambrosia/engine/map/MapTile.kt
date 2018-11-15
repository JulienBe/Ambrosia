package be.ambrosia.engine.map

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.g.GBatch
import be.ambrosia.getlost.Cst
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.collections.GdxArray
import ktx.collections.GdxSet

class MapTile private constructor() {
    val entities = GdxSet<Entity>()
    val elements = GdxArray<MapElement>()
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
        b.setColor(entities.size * 0.1f, entities.size * 0.015f, entities.size * 0.4f, 1f)
        b.draw(basicTexture, worldX, worldY, GameMap.tileSize, GameMap.tileSize)
        elements.forEach {
            it.draw(b, x, y)
        }
    }

    fun addEntity(entity: Entity): MapTile {
        entities.add(entity)
        return this
    }

    companion object {
        val basicTexture = AmbContext.cxt.inject<AssMan>().textureRegions[Cst.Cyclop.tr]
        val pool = object : Pool<MapTile>() {
            override fun newObject(): MapTile {
                return MapTile()
            }
        }
    }
}
package be.ambrosia.engine.map

import be.ambrosia.engine.g.GBatch
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.collections.GdxArray
import ktx.collections.GdxSet

class MapTile private constructor() {
    val entities = GdxSet<Entity>()
    private val elements = GdxArray<MapElement>()
    var elementMask = 0
    var x = 0
    var y = 0
    var worldX = 0f
    var worldY = 0f
    var worldCenterX = worldX + GameMap.tileHalfSize
    var worldCenterY = worldY + GameMap.tileHalfSize
    var worldRight = worldX + GameMap.tileSize
    var worldUp = worldY + GameMap.tileSize
    val tilesVerticalHorizontal = GdxArray<Pair<Int, Int>>()

    fun addElement(elem: MapElement) {
        elem.tile = this
        elements.add(elem)
        computeMask()
    }

    fun removeElement(elem: MapElement) {
        elements.removeValue(elem, true)
        computeMask()
    }

    private fun computeMask() {
        elementMask = 0
        elements.forEach {
            elementMask = it.id or elementMask
        }
    }


    fun set(x: Int, y: Int) {
        this.x = x
        this.y = y
        worldX = x * GameMap.tileSize
        worldY = y * GameMap.tileSize
        worldCenterX = worldX + GameMap.tileHalfSize
        worldCenterY = worldY + GameMap.tileHalfSize
        tilesVerticalHorizontal.add(Pair(x - 1, y))
        tilesVerticalHorizontal.add(Pair(x + 1, y))
        tilesVerticalHorizontal.add(Pair(x, y + 1))
        tilesVerticalHorizontal.add(Pair(x, y - 1))
        tilesVerticalHorizontal.shuffle()
    }

    fun draw(b: GBatch) {
        for (i in 0 until elements.size) {
            elements.get(i).draw(b)
        }
    }

    fun addEntity(entity: Entity): MapTile {
        entities.add(entity)
        return this
    }

    fun getElements(): GdxArray<MapElement> {
        return elements
    }

    fun <R> hasElement(klass: Class<R>): Boolean {
        return elements.any { klass.isInstance(it) }
    }

    companion object {

        val pool = object : Pool<MapTile>() {
            override fun newObject(): MapTile {
                return MapTile()
            }
        }
    }
}
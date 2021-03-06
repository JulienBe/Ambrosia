package be.ambrosia.engine.map

import be.ambrosia.engine.Dimensions
import be.ambrosia.engine.g.GBatch
import com.badlogic.gdx.math.MathUtils
import ktx.collections.GdxArray

object GameMap {
    val map = GdxArray<MapTile>(true, 1200)
    var tileSize = Dimensions.pixel * 25f
    var tileHalfSize = tileSize / 2f
    var w = 0
    var h = 0

    fun init(w: Int, h: Int) {
        this.w = w
        this.h = h
        for (x in 0 until w) // 3
            for (y in 0 until h) { // 2
                val t = MapTile.pool.obtain()
                t.set(x, y)
                map.add(t)
            }
    }

    fun addElement(x1: Int, y1: Int, x2: Int, y2: Int, obtain: (x: Int, y: Int) -> MapElement) {
        val beginX = Math.min(x1, x2)
        val endX = Math.max(x1, x2)
        val beginY = Math.min(y1, y2)
        val endY = Math.max(y1, y2)

        for (x in beginX..endX)
            for (y in beginY..endY) {
                getTile(x, y).addElement(obtain.invoke(x, y))
            }
    }

    fun addElement(x1: Int, y1: Int, x2: Int, y2: Int, element: MapElement) {
        val beginX = Math.min(x1, x2)
        val endX = Math.max(x1, x2)
        val beginY = Math.min(y1, y2)
        val endY = Math.max(y1, y2)

        for (x in beginX..endX)
            for (y in beginY..endY) {
                getTile(x, y).addElement(element)
            }
    }

    fun getTile(x: Int, y: Int): MapTile {
//        return map.get(x * h + y % h)
        return map.get(MathUtils.clamp(x, 0, w - 1) * w + MathUtils.clamp(y, 0, h - 1))
    }

    fun getX(index: Int): Int {
        return index / h
    }

    fun getY(index: Int): Int {
        return index % h
    }

    fun draw(b: GBatch) {
        map.forEach {
            it.draw(b)
        }
    }

    fun getTileInWorldCoord(x: Float, y: Float): MapTile {
        return getTile(MathUtils.clamp((x / tileSize).toInt(), 0, w), MathUtils.clamp((y / tileSize).toInt(), 0, h))
    }

    fun removeElement(x: Int, y: Int, mapElement: MapElement) {
        getTile(x, y).removeElement(mapElement)
    }

    fun clearEntitesOnTiles() {
        map.forEach { it.entities.clear() }
    }

}
package be.ambrosia.engine.map

import be.ambrosia.engine.Dimensions
import be.ambrosia.engine.g.GBatch
import ktx.collections.GdxArray

object GameMap {
    val map = GdxArray<MapTile>(true, 32)
    var tileSize = Dimensions.pixel * 25f
    var w = 0
    var h = 0

    fun init(w: Int, h: Int) {
        this.w = w
        this.h = h
        for (x in 0..w) // 3
            for (y in 0..h)  // 2
                map.add(MapTile.pool.obtain())
//        0 -> 0 0
//        1 -> 0 1
//        2 -> 1 0
//        3 -> 1 1
//        4 -> 2 0
    }

    fun addElement(x1: Int, y1: Int, x2: Int, y2: Int, element: MapElement) {
        val beginX = Math.min(x1, x2)
        val endX = Math.max(x1, x2)
        val beginY = Math.min(y1, y2)
        val endY = Math.max(y1, y2)
        for (x in beginX..endX)
            for (y in beginY..endY) {
                getTile(x, y).elements.add(element)
            }
    }

    fun getTile(x: Int, y: Int): MapTile {
        return map.get(x * h + y % h)
    }

    fun getX(index: Int): Int {
        return index / h
    }

    fun getY(index: Int): Int {
        return index % h
    }

    fun draw(b: GBatch) {
        map.forEachIndexed { index, mapTile ->
            mapTile.draw(b, getX(index), getY(index))
        }
    }

}
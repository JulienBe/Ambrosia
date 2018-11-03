package be.ambrosia.engine.map

import be.ambrosia.engine.g.GBatch

abstract class MapElement(val index : Int) {

    open var draw: (GBatch, x: Int, y: Int) -> Unit = {gBatch, x, y ->  }

    companion object {
        val empty = 0
        val floor = 1
        val wall = 2
    }

}
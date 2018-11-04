package be.ambrosia.engine.map

import be.ambrosia.engine.g.GBatch

abstract class MapElement(val index : Int) {

    open var draw: (GBatch, x: Int, y: Int) -> Unit = {gBatch, x, y ->  }

    fun setDraw(kFunction3: (GBatch, Int, Int) -> Unit): MapElement {
        this.draw = kFunction3
        return this
    }


    companion object {
        val empty = 0
        val floor = 1
        val wall = 2
    }

}
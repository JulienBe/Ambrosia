package be.ambrosia.engine.map

import be.ambrosia.engine.g.GBatch

abstract class MapElement(val id: Int) {

    lateinit var tile: MapTile
    open var draw: (GBatch) -> Boolean = {gBatch -> true }

    fun setDraw(kFunction: (GBatch) -> Boolean): MapElement {
        this.draw = kFunction
        return this
    }


    companion object {
        const val empty = 0
        const val floor = 1
        const val wall = 2
    }

}
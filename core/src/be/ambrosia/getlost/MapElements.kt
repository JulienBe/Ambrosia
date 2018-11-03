package be.ambrosia.getlost

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.g.GColor
import be.ambrosia.engine.map.GameMap
import be.ambrosia.engine.map.elements.Wall

object MapElements {

    val wallColor = GColor.convertARGB(1f, 0.2f, 0.2f, 0.2f)
    val assMan = AmbContext.cxt.inject<AssMan>()
    val tr = assMan.textureRegions["debris"]
    val worldW = 30

    fun init() {
        Wall.draw = ::wallDraw
        GameMap.init(worldW, worldW)
        GameMap.addElement(0,           0,               0, worldW - 1, Wall)
        GameMap.addElement(worldW - 1,  worldW - 1,      0, worldW - 1, Wall)
        GameMap.addElement(worldW - 1,  worldW - 1,      worldW - 1, 0, Wall)
        GameMap.addElement(0,           0,      worldW - 1, 0, Wall)
    }

    fun wallDraw(batch: GBatch, x: Int, y: Int) {
        batch.setColor(wallColor)
        batch.draw(tr, GameMap.tileSize * x, GameMap.tileSize * y, GameMap.tileSize, GameMap.tileSize)
    }
}
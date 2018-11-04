package be.ambrosia.getlost

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.g.GColor
import be.ambrosia.engine.g.GSide
import be.ambrosia.engine.map.GameMap
import be.ambrosia.engine.map.elements.Wall

object MapElements {

    val wallColor = GColor.convertARGB(1f, 0.2f, 0.2f, 0.2f)
    val assMan = AmbContext.cxt.inject<AssMan>()
    val tr = assMan.textureRegions["debris"]
    val worldW = 39

    fun init() {
        val wallTop = Wall.obtain(GSide.TOP)

        GameMap.init(worldW, worldW)
        GameMap.addElement(0,           0,              0, worldW - 1, Wall.obtain(GSide.RIGHT).setDraw(::wallDraw))
        GameMap.addElement(worldW - 1,  worldW - 1,     0, worldW - 1, Wall.obtain(GSide.BOTTOM).setDraw(::wallDraw))
        GameMap.addElement(worldW - 1,  worldW - 1,     worldW - 1, 0, Wall.obtain(GSide.LEFT).setDraw(::wallDraw))
        GameMap.addElement(0,           0,              worldW - 1, 0, Wall.obtain(GSide.TOP).setDraw(::wallDraw))
    }

    fun wallDraw(batch: GBatch, x: Int, y: Int) {
        batch.setColor(wallColor)
        batch.draw(tr, GameMap.tileSize * x, GameMap.tileSize * y, GameMap.tileSize, GameMap.tileSize)
    }
}
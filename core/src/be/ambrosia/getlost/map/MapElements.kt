package be.ambrosia.getlost.map

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.g.GColor
import be.ambrosia.engine.g.collisions.GSide
import be.ambrosia.engine.map.GameMap
import be.ambrosia.engine.map.globalelems.Wall

object MapElements {

    val wallColor = GColor.convertARGB(1f, 0.2f, 0.2f, 0.2f)
    val assMan = AmbContext.cxt.inject<AssMan>()
    val tr = assMan.textureRegions["debris"]
    val worldW = 38

    fun init() {
        GameMap.init(worldW, worldW)
        GameMap.addElement(0,           0,              0, worldW - 1, ::addWallRight)
        GameMap.addElement(worldW - 1,  worldW - 1,     0, worldW - 1, ::addWallBottom)
        GameMap.addElement(worldW - 1,  worldW - 1,     worldW - 1, 0, ::addWallLeft)
        GameMap.addElement(0,           0,              worldW - 1, 0, ::addWallTop)
    }

    fun addWallRight(x: Int, y: Int): Wall {
        return addWall(GSide.RIGHT)
    }
    fun addWallLeft(x: Int, y: Int): Wall {
        return addWall(GSide.LEFT)
    }
    fun addWallTop(x: Int, y: Int): Wall {
        return addWall(GSide.TOP)
    }
    fun addWallBottom(x: Int, y: Int): Wall {
        return addWall(GSide.BOTTOM)
    }
    fun addWall(side: GSide): Wall {
        val wall = Wall.obtain(side)
        wall.setDraw { b -> wallDraw(b, wall) }
        return wall
    }

    fun wallDraw(batch: GBatch, wall: Wall) {
        batch.setColor(wallColor)
        batch.draw(tr, wall.tile.worldX, wall.tile.worldY, GameMap.tileSize, GameMap.tileSize)
    }


}
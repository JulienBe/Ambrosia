package be.ambrosia.getlost.map

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.g.GRand
import be.ambrosia.engine.g.GTime
import be.ambrosia.engine.map.GameMap
import be.ambrosia.engine.map.MapElement
import be.ambrosia.engine.map.MapTile
import be.ambrosia.engine.map.globalelems.Wall
import be.ambrosia.getlost.Cst
import com.badlogic.gdx.utils.Pool

class Energy private constructor() : MapElement(energyId) {
    var energy = 0
    var energySpread = 0f
    var shuffle = false

    companion object {
        val basicTexture = AmbContext.cxt.inject<AssMan>().textureRegions[Cst.Cyclop.tr]
        var energySpreadSpeed = 2f
        val rand = AmbContext.cxt.inject<GRand>()
        const val energyId = MapElement.wall * 2

        private val pool = object : Pool<Energy>() {
            override fun newObject(): Energy {
                return Energy()
            }
        }

        fun obtain(amount: Int): Energy {
            val e = pool.obtain()
            e.setDraw { gBatch ->
                draw(gBatch, e)
                e.energy > 0
            }
            e.energy = amount
            return e
        }

        fun draw(batch: GBatch, e: Energy) {
            e.energySpread += GTime.delta
            batch.setColor(Math.min(e.energy * 0.1f, 1f), Math.min(e.energy * 0.05f, 1f), Math.min(e.energy * 0.2f, 1f), 1f)
            batch.draw(basicTexture, e.tile.worldX, e.tile.worldY, GameMap.tileSize, GameMap.tileSize)
            if (e.energy > 0 && e.energySpread > energySpreadSpeed) {
                e.energySpread = -rand.float(0.5f)
                if (e.shuffle) {
                    e.tile.tilesVerticalHorizontal.shuffle()
                    e.shuffle = false
                }
                e.tile.tilesVerticalHorizontal.forEach {
                    checkTileForEnergySpread(GameMap.getTile(it.first, it.second), e)
                }
            }
        }

        private fun checkTileForEnergySpread(other: MapTile, e: Energy) {
            if (other.hasElement(Wall::class.java)) {
                e.shuffle = true
                return
            }
            val energy = other.getElements().filterIsInstance(Energy::class.java)
            if (energy.isEmpty()) {
                val add = e.energy / 4
                other.addElement(Energy.obtain(add))
                e.energy -= add
            } else {
                if (energy.first().energy < e.energy) {
                    val transfer = Math.min(e.energy / 4, e.energy - energy.first().energy)
                    e.energy -= transfer
                    energy.first().energy += transfer
                }
            }
        }
    }
}
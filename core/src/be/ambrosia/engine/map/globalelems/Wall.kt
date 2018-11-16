package be.ambrosia.engine.map.globalelems

import be.ambrosia.engine.g.collisions.GSide
import be.ambrosia.engine.map.MapElement
import com.badlogic.gdx.utils.Pool

class Wall private constructor() : MapElement(wall) {
    var exposedSide = GSide.NONE

    companion object {
        private val pool = object : Pool<Wall>() {
            override fun newObject(): Wall {
                return Wall()
            }
        }
        fun obtain(exposedSide: GSide): Wall {
            val w = pool.obtain()
            w.exposedSide = exposedSide
            return w
        }
    }
}
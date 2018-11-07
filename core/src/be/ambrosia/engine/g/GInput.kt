package be.ambrosia.engine.g

import be.ambrosia.engine.Dimensions
import com.badlogic.gdx.Gdx

object GInput {
    fun clicX(): Float {
        return Gdx.input.x.toFloat()
    }
    fun clicY(): Float {
        return Dimensions.gameHeight - Gdx.input.y
    }
}
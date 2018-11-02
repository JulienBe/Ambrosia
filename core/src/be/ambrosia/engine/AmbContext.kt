package be.ambrosia.engine

import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.g.GRand
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.ModelBatch
import ktx.inject.Context

object AmbContext {

    val cxt = Context()

    init {
        cxt.register {
            bindSingleton { GRand() }
            bindSingleton { ModelBatch() }
            bindSingleton { GBatch(5500) }
            bindSingleton { PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()) }
        }
    }
}
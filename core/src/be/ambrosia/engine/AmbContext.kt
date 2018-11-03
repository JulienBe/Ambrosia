package be.ambrosia.engine

import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.g.GRand
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import ktx.inject.Context

object AmbContext {

    val cxt = Context()

    init {
        val w = World(Vector2(0f, 0f), true)
        val listener = B2DListener()
        w.setContactListener(listener)
//        val cam = PerspectiveCamera(67f, Dimensions.gameWidth, Dimensions.gameHeight)
//        cam.position.setSpeed(0f, 0f, 0f)
//        cam.lookAt(0f, 0f, -20f)
//        cam.near = 1f
//        cam.far = 300f
        val cam = OrthographicCamera(Dimensions.gameWidth, Dimensions.gameHeight)
        cam.position.set(Dimensions.gameHW, Dimensions.gameHH, 0f)
        cxt.register {
            bindSingleton { GRand() }
            bindSingleton { ModelBatch() }
            bindSingleton { GBatch(5500) }
            bindSingleton { cam }
            bindSingleton { w }
            bindSingleton { listener }
            bindSingleton { Box2DDebugRenderer() }
        }

    }
}
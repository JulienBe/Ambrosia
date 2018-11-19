package be.ambrosia.engine.g

import be.ambrosia.engine.AmbContext
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3

object GInput {
    val cam: OrthographicCamera = AmbContext.cxt.inject()
    val mouse = Vector3(0f, 0f, 0f)

    fun clicX(): Float {
        mouse.x = Gdx.input.x.toFloat()
        mouse.y = Gdx.input.y.toFloat()
        return cam.unproject(mouse).x
    }
    fun clicY(): Float {
        mouse.x = Gdx.input.x.toFloat()
        mouse.y = Gdx.input.y.toFloat()
        return cam.unproject(mouse).y
    }
}
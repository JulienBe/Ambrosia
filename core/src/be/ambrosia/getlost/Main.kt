package be.ambrosia.getlost

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.g.GBatch
import be.ambrosia.getlost.templates.Cyclop
import be.ambrosia.getlost.templates.CyclopSpwaner
import be.ambrosia.getlost.templates.Player
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.ModelBatch
import ktx.app.KtxScreen

class Main : KtxScreen {

    val b: GBatch = AmbContext.cxt.inject()
    val b3D: ModelBatch = AmbContext.cxt.inject()
    val cam: PerspectiveCamera = AmbContext.cxt.inject()

    init {
        cam.position.set(0f, 0f, 50f)
        cam.lookAt(0f, 0f, -1f)
        cam.near = 1f
        cam.far = 300f
        cam.update()
        ECSEngine.addEntity(Player.init(ECSEngine.createEntity()))
        ECSEngine.addEntity(CyclopSpwaner.init(ECSEngine.createEntity()))
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        b3D.begin(cam)
        b.begin()
        ECSEngine.update(Gdx.graphics.deltaTime)
        b.end()
        b3D.end()
    }
}
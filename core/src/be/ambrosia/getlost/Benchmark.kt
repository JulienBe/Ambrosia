package be.ambrosia.getlost

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.Dimensions
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.systems.Drawing2DSystem
import be.ambrosia.engine.ecs.systems.Drawing3DSystem
import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.g.GRand
import be.ambrosia.engine.map.GameMap
import be.ambrosia.getlost.templates.Cyclop
import be.ambrosia.getlost.templates.Energy
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import ktx.app.KtxScreen

class Benchmark : KtxScreen {

    val b: GBatch = AmbContext.cxt.inject()
    val cam: OrthographicCamera = AmbContext.cxt.inject()
    val rand = AmbContext.cxt.inject<GRand>()

    init {
        rand.setSeed(1L)
        println("running benchmark ${rand.nextFloat()}")
        MapElements.init()
        cam.update()
        for (i in 0..5000) {
//        for (i in 0..5) {
            ECSEngine.addEntity(Cyclop.init(ECSEngine.createEntity(), 300f, 300f))
        }
//        ECSEngine.addEntity(Energy.init(ECSEngine.createEntity(), 350f, 350f))
//        ECSEngine.addEntity(Energy.init(ECSEngine.createEntity(), 360f, 350f))
//        ECSEngine.addEntity(Energy.init(ECSEngine.createEntity(), 260f, 360f))
//        ECSEngine.addEntity(Energy.init(ECSEngine.createEntity(), 350f, 256f))
//        ECSEngine.addEntity(Energy.init(ECSEngine.createEntity(), 450f, 450f))
        Cyclop.reproduceTimerValue = 10000f
    }

    override fun render(delta: Float) {
        Gdx.app.log("fps", "${Gdx.graphics.framesPerSecond}")
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        cam.update()
        b.projectionMatrix = cam.combined
        b.begin()
        GameMap.draw(b)
        GameMap.clearEntitesOnTiles()
        ECSEngine.update(Gdx.graphics.deltaTime)
        b.end()
    }

}
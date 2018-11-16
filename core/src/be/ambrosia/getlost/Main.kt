package be.ambrosia.getlost

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.B2DGears
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.g.GTime
import be.ambrosia.engine.map.GameMap
import be.ambrosia.getlost.map.MapElements
import be.ambrosia.getlost.templates.CyclopSpwaner
import be.ambrosia.getlost.templates.Player
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import ktx.app.KtxScreen

class Main : KtxScreen {

    val b: GBatch = AmbContext.cxt.inject()
    val b3D: ModelBatch = AmbContext.cxt.inject()
    val cam: OrthographicCamera = AmbContext.cxt.inject()
    val debugRenderer = AmbContext.cxt.inject<Box2DDebugRenderer>()
    val box2D = B2DGears()

    init {
        MapElements.init()
        cam.update()
        ECSEngine.addEntity(Player.init(ECSEngine.createEntity()))
        ECSEngine.addEntity(CyclopSpwaner.init(ECSEngine.createEntity()))
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        cam.update()
        b.projectionMatrix = cam.combined
        GTime.majDeltas(delta, delta)
//        b3D.begin(cam)
        b.begin()
        GameMap.draw(b)
        GameMap.clearEntitesOnTiles()
        ECSEngine.update(Gdx.graphics.deltaTime)
//        box2D.doPhysicsStep(Gdx.graphics.deltaTime)
//        debugRenderer.render(world, cam.combined)
        b.end()
//        b3D.end()
    }




}
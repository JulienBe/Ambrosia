package be.ambrosia.getlost

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.B2DGears
import be.ambrosia.engine.state.GameState
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.g.GTime
import be.ambrosia.engine.map.GameMap
import be.ambrosia.engine.state.State
import be.ambrosia.getlost.map.Energy
import be.ambrosia.getlost.map.MapElements
import be.ambrosia.getlost.templates.CyclopSpwaner
import be.ambrosia.getlost.templates.Player
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
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
        State.register {
            when (it) {
                GameState.LOST -> {
                    ECSEngine.removeAllEntities()
                    GameMap.map.forEach {
                        it.getElements().filterIsInstance<Energy>().forEach {
                            it.energy = 0
                        }
                    }
                }
                GameState.WAITING -> init()
            }
        }
        State.changeState(GameState.WAITING)
    }

    fun init() {
        ECSEngine.addEntity(Player.init(ECSEngine.createEntity()))
        ECSEngine.addEntity(CyclopSpwaner.init(ECSEngine.createEntity()))
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        cam.update()
        b.projectionMatrix = cam.combined
        b.begin()
        when (State.current) {
            GameState.WAITING -> {
                GTime.majDeltas(0f, 0f)
                GameMap.draw(b)
                GameMap.clearEntitesOnTiles()
                ECSEngine.update(0f)
                if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
                    State.changeState(GameState.RUNNING)
            }
            GameState.RUNNING -> {
                GTime.majDeltas(delta, delta)
                GameMap.draw(b)
                GameMap.clearEntitesOnTiles()
                ECSEngine.update(delta)
            }
            GameState.LOST -> {
                GTime.majDeltas(0f, 0f)
                State.changeState(GameState.WAITING)
                ECSEngine.update(0f)
            }
        }
        b.end()
    }




}
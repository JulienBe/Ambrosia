package be.ambrosia.getlost

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.systems.*
import com.badlogic.gdx.Screen
import ktx.app.KtxGame

class GetLost : KtxGame<Screen>() {

    override fun create() {
        AmbContext.cxt.bindSingleton {
            AssMan("atlas/textures.atlas", listOf<String>("debris"))
        }
        ECSEngine.addSystem(MovementSystem())
        ECSEngine.addSystem(SensorSystem())
        ECSEngine.addSystem(TileBinderSystem())
        ECSEngine.addSystem(CollisionSystem())
        ECSEngine.addSystem(Drawing2DSystem())
//        ECSEngine.addSystem(Drawing3DSystem())
        ECSEngine.addSystem(ControlSystem())
        ECSEngine.addSystem(TimeSystem())
        ECSEngine.addSystem(WandererSystem())
        ECSEngine.addSystem(SpawnSystem())
        ECSEngine.addSystem(BodySystem())

        addScreen(Benchmark())
        setScreen<Benchmark>()
    }

}

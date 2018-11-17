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
        ECSEngine.addSystem(ControlSystem())
        ECSEngine.addSystem(ActSystem())
        ECSEngine.addSystem(MovementSystem())
        ECSEngine.addSystem(TileBinderSystem())
        ECSEngine.addSystem(SensorSystem())
        ECSEngine.addSystem(CollisionSystem())
//        ECSEngine.addSystem(Drawing3DSystem())
        ECSEngine.addSystem(TimeSystem())
        ECSEngine.addSystem(WandererSystem())
        ECSEngine.addSystem(SpawnSystem())
        ECSEngine.addSystem(BodySystem())
        ECSEngine.addSystem(Drawing2DSystem())

        addScreen(Main())
        setScreen<Main>()
//        addScreen(Benchmark())
//        setScreen<Benchmark>()
    }

}

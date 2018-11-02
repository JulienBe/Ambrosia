package be.ambrosia.engine.particles

import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.ParticleComp
import com.badlogic.gdx.utils.Pool
import ktx.ashley.add
import ktx.ashley.entity

class Particle3D private constructor(): Particle() {

    init {
        ECSEngine.add {
            entity {
                with<ParticleComp> {}
            }
        }
    }

    override fun tick() {
    }
    companion object {
        val pool = object : Pool<Particle3D>() {
            override fun newObject(): Particle3D {
                return Particle3D()
            }
        }
    }
}
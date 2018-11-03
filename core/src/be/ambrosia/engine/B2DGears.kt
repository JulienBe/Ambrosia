package be.ambrosia.engine

import com.badlogic.gdx.physics.box2d.World

class B2DGears {
    private val world = AmbContext.cxt.inject<World>()
    private val timestep = 1/60f
    private val velocityITerations = 6
    private val positionIteration = 2
    private var accumulator = 0f

    fun doPhysicsStep(deltaTime: Float) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        val frameTime = Math.min(deltaTime, 0.25f)
        accumulator += frameTime
        while (accumulator >= timestep) {
            world.step(timestep, velocityITerations, positionIteration)
            accumulator -= timestep
        }
    }
}
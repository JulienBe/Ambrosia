package be.ambrosia.engine.particles

abstract class Particle {
    var x = 0f
    var y = 0f
    abstract fun tick()
}
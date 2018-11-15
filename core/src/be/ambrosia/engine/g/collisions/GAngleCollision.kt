package be.ambrosia.engine.g.collisions

class GAngleCollision(first: Float, second: Float, val side: GSide) {
    val max = Math.max(first, second)
    val min = Math.min(first, second)
    val diff = max - min

}
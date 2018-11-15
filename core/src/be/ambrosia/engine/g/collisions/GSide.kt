package be.ambrosia.engine.g.collisions

import com.badlogic.gdx.math.Vector2

enum class GSide(val bounceV: Vector2, val normal: Float = bounceV.angle()) {
    LEFT(Vector2(-1f, 0f)), RIGHT(Vector2(1f, 0f)), TOP(Vector2(0f, 1f)), BOTTOM(Vector2(0f, -1f)), NONE(Vector2())
}
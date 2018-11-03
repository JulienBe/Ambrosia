package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.Dimensions
import com.badlogic.gdx.math.Vector2
import ktx.ashley.mapperFor

class DirComp(var minSpeed: Float = 0f, var maxSpeed: Float = 2000f) : TemplateComp {

    private val dir = Vector2(0f, 0f)
    var previousDir = Vector2(dir)

    val dirX: Float get() = dir.x
    val dirY: Float get() = dir.y

    fun validate() {
        previousDir.x = dir.x
        previousDir.y = dir.y
    }

    fun setDir(x: Float, y: Float) {
        dir.x = x
        dir.y = y
    }

    fun addX(x: Float) {
        dir.x += x
    }

    fun addY(y: Float) {
        dir.y += y
    }

    fun setSpeed(min: Float, max: Float) {
        minSpeed = min * Dimensions.pixel
        maxSpeed = max * Dimensions.pixel
    }

    fun setDirLength(l: Float) {
        dir.setLength(l)
    }

    companion object {
        val mapper = mapperFor<DirComp>()
    }
}
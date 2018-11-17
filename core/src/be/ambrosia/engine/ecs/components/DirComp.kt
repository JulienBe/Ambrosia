package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.Dimensions
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import ktx.ashley.mapperFor

class DirComp(var minSpeed: Float = 0f, var maxSpeed: Float = 2000f) : TemplateComp {

    private val dir = Vector2(0f, 0f)
    var previousDir = Vector2(dir)

    val dirX: Float get() = dir.x
    val dirY: Float get() = dir.y

    fun clampSpeed(min: Float, max: Float) {
        dir.setLength(MathUtils.clamp(dir.len(), min, max))
    }
    fun clampSpeed() {
        clampSpeed(minSpeed, maxSpeed)
    }

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

    override fun reset() {
        super.reset()
        dir.set(0f, 0f)
        previousDir.set(0f, 0f)
    }

    fun setDirAndKeepSpeed(x: Float, y: Float) {
        val pLenght = dir.len()
        dir.set(x, y)
        dir.setLength(pLenght)
    }

    companion object {
        val mapper = mapperFor<DirComp>()
    }
}
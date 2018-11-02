package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.Dimensions
import com.badlogic.gdx.math.Vector2
import ktx.ashley.mapperFor

class DirComp(var dir: Vector2 = Vector2(0f, 0f), var minSpeed: Float = 0f, var maxSpeed: Float = 2000f) : TemplateComp {
    fun set(min: Float, max: Float) {
        minSpeed = min * Dimensions.pixel
        maxSpeed = max * Dimensions.pixel
    }

    companion object {
        val mapper = mapperFor<DirComp>()
    }
}
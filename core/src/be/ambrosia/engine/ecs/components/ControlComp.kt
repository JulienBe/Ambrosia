package be.ambrosia.engine.ecs.components

import com.badlogic.gdx.Input
import ktx.ashley.mapperFor

class ControlComp : TemplateComp {
    var left = listOf<Int>(
            Input.Keys.A,
            Input.Keys.Q,
            Input.Keys.LEFT
    )
    var right = listOf<Int>(
            Input.Keys.D,
            Input.Keys.RIGHT
    )
    var up = listOf<Int>(
            Input.Keys.Z,
            Input.Keys.UP,
            Input.Keys.W
    )
    var down = listOf<Int>(
            Input.Keys.S,
            Input.Keys.DOWN
    )
    companion object {
        val mapper = mapperFor<ControlComp>()
    }
}
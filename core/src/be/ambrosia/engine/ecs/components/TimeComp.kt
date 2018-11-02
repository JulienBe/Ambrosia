package be.ambrosia.engine.ecs.components

import ktx.ashley.mapperFor

class TimeComp(var delta: Float = 0f, var total: Float = 0f, var player: Boolean = true) : TemplateComp {
    override fun reset() {
        super.reset()
        delta = 0f
        total = 0f
        player = false
    }

    companion object {
        val mapper = mapperFor<TimeComp>()
    }
}
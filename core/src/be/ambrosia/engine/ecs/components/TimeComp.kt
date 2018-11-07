package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.Timer
import ktx.ashley.mapperFor
import ktx.collections.GdxMap

class TimeComp(var delta: Float = 0f, var total: Float = 0f, var player: Boolean = true) : TemplateComp {

    val timers = GdxMap<String, Timer>()

    override fun reset() {
        super.reset()
        delta = 0f
        total = 0f
        player = false
        timers.entries().forEach { it.value.free() }
        timers.clear()
    }

    companion object {
        val mapper = mapperFor<TimeComp>()
    }
}
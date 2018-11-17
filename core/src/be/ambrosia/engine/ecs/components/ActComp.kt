package be.ambrosia.engine.ecs.components

import com.badlogic.ashley.core.Entity
import ktx.ashley.mapperFor

class ActComp : TemplateComp {

    var onAct: (Entity) -> Unit = {
    }
    companion object {
        val mapper = mapperFor<ActComp>()
    }
}
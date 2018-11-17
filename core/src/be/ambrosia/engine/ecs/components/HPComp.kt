package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.AmbContext
import com.badlogic.gdx.physics.box2d.*
import ktx.ashley.mapperFor
import ktx.box2d.body

class HPComp : TemplateComp {

    var hp = 0

    companion object {
        val mapper = mapperFor<HPComp>()
        val world = AmbContext.cxt.inject<World>()
    }
}
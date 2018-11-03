package be.ambrosia.engine.ecs.components

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Rectangle
import ktx.ashley.mapperFor

class ColliderComp : TemplateComp {

    var pushBack = true
    var pushBounce = true

    var colliding: (Entity, Entity) -> Unit = {
        me, other ->
    }


    fun collidesWith(me: Entity, other: Entity) {
        colliding.invoke(me, other)
    }

    val rectangle = Rectangle()

    companion object {
        val mapper = mapperFor<ColliderComp>()
    }
}
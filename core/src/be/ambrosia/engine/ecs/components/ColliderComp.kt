package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.map.MapElement
import be.ambrosia.engine.map.MapTile
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Rectangle
import ktx.ashley.mapperFor
import ktx.collections.GdxArray

class ColliderComp : TemplateComp {

    var id = 0
    var pushBack = true
    var pushBounce = true
    val tileElementColliding = GdxArray<Int>()
    var collidingWith = 0

    var colliding: (Entity, Entity) -> Unit = {
        me, other ->
    }

    var collidingTile: (Entity, MapTile, MapElement) -> Unit = {
        entity, mapTile, mapElement ->
    }

    fun collidesWith(me: Entity, other: Entity) {
        colliding.invoke(me, other)
    }

    val rectangle = Rectangle()

    companion object {
        val mapper = mapperFor<ColliderComp>()
    }
}
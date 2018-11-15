package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.map.MapElement
import be.ambrosia.engine.map.MapTile
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import ktx.ashley.mapperFor
import ktx.collections.GdxArray

class SensorComp : TemplateComp {

    var id = 0
    var collidingWith = 0
    var circle = Circle()

    var sensing: (Entity, Entity) -> Unit = {
        me, other ->
    }

    var collidingTile: (Entity, MapTile, MapElement) -> Unit = {
        entity, mapTile, mapElement ->
    }

    val rectangle = Rectangle()

    companion object {
        val mapper = mapperFor<SensorComp>()
    }
}
package be.ambrosia.engine.ecs

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine

object ECSEngine : PooledEngine() {
    fun <T : Component> createComponent(java: Class<T>, entity: Entity): T {
        val comp = createComponent(java)
        entity.add(comp)
        return comp
    }
}
package be.ambrosia.engine.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool

interface TemplateComp : Component, Pool.Poolable {
    override fun reset() {
    }
}
package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.ControlComp
import be.ambrosia.engine.ecs.components.DirComp
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import ktx.ashley.allOf

class ControlSystem : IteratingSystem(family) {

    val controlMapper = ControlComp.mapper
    val dirMapper = DirComp.mapper

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val dir = dirMapper.get(entity)
        val control = controlMapper.get(entity)
        dir.dir.set(0f, 0f)
        if (checkKeys(control.left))
            dir.dir.x += -1f
        if (checkKeys(control.right))
            dir.dir.x += 1f
        if (checkKeys(control.down))
            dir.dir.y += -1f
        if (checkKeys(control.up))
            dir.dir.y += 1f
        dir.dir.setLength(1f * dir.maxSpeed)
    }

    fun checkKeys(keys: List<Int>): Boolean {
        keys.forEach {
            if (Gdx.input.isKeyPressed(it))
                return true
        }
        return false
    }

    companion object {
        val family: Family = allOf(
                DirComp::class,
                ControlComp::class).get()
    }
}
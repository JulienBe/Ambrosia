package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.components.ColliderComp
import be.ambrosia.engine.ecs.components.ControlComp
import be.ambrosia.engine.ecs.components.DirComp
import be.ambrosia.engine.ecs.components.PosComp
import be.ambrosia.engine.g.GBench
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import ktx.ashley.allOf

class ControlSystem : IteratingSystem(family) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val dir = dirMapper.get(entity)
        val control = controlMapper.get(entity)
        val pos = posMapper.get(entity)
        val collider = colliderMapper.get(entity)
        dir.setDir(0f, 0f)
        if (checkKeys(control.left))
            dir.addX(-1f)
        if (checkKeys(control.right))
            dir.addX(1f)
        if (checkKeys(control.down))
            dir.addY(-1f)
        if (checkKeys(control.up))
            dir.addY(1f)
        dir.setDirLength(1f * dir.maxSpeed)
        if (Gdx.input.justTouched())
            control.onClick.invoke()
        pos.tileSet.forEach { tile ->
            tile.getElements().forEach {
                if (collider.collidingWithTiles and it.id != 0)
                    collider.collidingTile(entity, tile, it)
            }
            tile.entities.remove(entity)
        }
    }

    fun checkKeys(keys: List<Int>): Boolean {
        keys.forEach {
            if (Gdx.input.isKeyPressed(it))
                return true
        }
        return false
    }

    companion object {
        val controlMapper = ControlComp.mapper
        val dirMapper = DirComp.mapper
        val posMapper = PosComp.mapper
        val colliderMapper = ColliderComp.mapper
        val bench = GBench("control")

        val family: Family = allOf(
                PosComp::class,
                DirComp::class,
                ColliderComp::class,
                ControlComp::class).get()
    }
}
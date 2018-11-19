package be.ambrosia.getlost.templates

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.ecs.systems.CollisionSystem
import be.ambrosia.engine.g.GColor
import be.ambrosia.engine.g.GInput
import be.ambrosia.engine.map.MapElement
import be.ambrosia.engine.map.globalelems.Wall
import be.ambrosia.engine.particles.Particle
import be.ambrosia.engine.particles.Particle3D
import be.ambrosia.getlost.Cst
import be.ambrosia.getlost.Ids
import be.ambrosia.getlost.Layers
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.OrthographicCamera

object Player {

    val assMan: AssMan = AmbContext.cxt.inject()
    val tr = assMan.textureRegions[Cst.Player.tr]
    val color = GColor.convertARGB(1f, 0.5f, 0.5f, 0.5f)

    fun init(entity: Entity): Entity {
        ECSEngine.createComponent(TimeComp::class.java, entity)
        val pos = ECSEngine.createComponent(PosComp::class.java, entity)
        val dir = ECSEngine.createComponent(DirComp::class.java, entity)
        val draw = ECSEngine.createComponent(Drawable2DComp::class.java, entity)
        val control = ECSEngine.createComponent(ControlComp::class.java, entity)
        val collider = ECSEngine.createComponent(ColliderComp::class.java, entity)
        val trail = ECSEngine.createComponent(TrailComp::class.java, entity)

        collider.pushBack = true
        collider.pushBounce = true
        collider.collidingWithTiles = MapElement.wall
        collider.collidingTile = {entity, mapTile, mapElement ->
            if (mapElement is Wall)
                CollisionSystem.wallCollideAndStick(pos, mapElement, dir)
        }
        collider.collidingWith = Ids.cyclop
        collider.id = Ids.player

        pos.set(Cst.Player.w, Cst.Player.w)
        dir.setSpeed(Cst.Player.speed, Cst.Player.speed)

        pos.z = Layers.player
        draw.draw = {
            it.setColor(color)
            it.draw(tr, pos.x, pos.y, pos.w, pos.h)
        }

        control.onClick = {
            ECSEngine.addEntity(
                PlayerShot.init(ECSEngine.createEntity(), pos.x, pos.y, GInput.clicX() - pos.x, GInput.clicY() - pos.y)
            )
        }

        trail.draw = { gBatch, entity ->
            val pos = PosComp.mapper[entity]
            gBatch.setColor(color)
            gBatch.draw(tr, pos.x, pos.y, pos.w, pos.h)
        }
        return entity
    }

    fun emitParticle(): Particle {
        val p = Particle3D.pool.obtain()
        return p
    }

}
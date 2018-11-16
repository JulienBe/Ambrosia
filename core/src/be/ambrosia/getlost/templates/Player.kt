package be.ambrosia.getlost.templates

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.ecs.systems.CollisionSystem
import be.ambrosia.engine.g.GInput
import be.ambrosia.engine.map.MapElement
import be.ambrosia.engine.map.globalelems.Wall
import be.ambrosia.engine.particles.Particle
import be.ambrosia.engine.particles.Particle3D
import be.ambrosia.getlost.Cst
import be.ambrosia.getlost.Ids
import be.ambrosia.getlost.Layers
import com.badlogic.ashley.core.Entity

object Player {

    val assMan: AssMan = AmbContext.cxt.inject()

    fun init(entity: Entity): Entity {
        val pos = ECSEngine.createComponent(PosComp::class.java)
        val dir = ECSEngine.createComponent(DirComp::class.java)
        val time = ECSEngine.createComponent(TimeComp::class.java)
        val draw = ECSEngine.createComponent(Drawable2DComp::class.java)
        val control = ECSEngine.createComponent(ControlComp::class.java)
        val emitter = ECSEngine.createComponent(ParticleEmitter::class.java)
        val collider = ECSEngine.createComponent(ColliderComp::class.java)

        collider.pushBack = true
        collider.collidingWithTiles = MapElement.wall
        collider.collidingTile = {entity, mapTile, mapElement ->
            if (mapElement is Wall)
                CollisionSystem.wallCollision(PosComp.mapper.get(entity), DirComp.mapper.get(entity), mapElement, mapTile)
        }
        collider.collidingWith = Ids.cyclop
        collider.id = Ids.player

        pos.set(Cst.Player.w, Cst.Player.w)
        dir.setSpeed(Cst.Player.speed, Cst.Player.speed)

        pos.z = Layers.player
        draw.batch = AmbContext.cxt.inject()
        draw.tr = assMan.textureRegions[Cst.Player.tr]

        control.onClick = {
            ECSEngine.addEntity(
                PlayerShot.init(ECSEngine.createEntity(), pos.x, pos.y, GInput.clicX() - pos.x, GInput.clicY() - pos.y)
            )
        }

        entity.add(pos).add(dir).add(time).add(draw).add(control).add(emitter).add(collider)
        return entity
    }

    fun emitParticle(): Particle {
        val p = Particle3D.pool.obtain()
        return p
    }

}
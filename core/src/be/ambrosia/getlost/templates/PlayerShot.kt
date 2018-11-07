package be.ambrosia.getlost.templates

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.Timer
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.ecs.systems.CollisionSystem
import be.ambrosia.engine.map.MapElement
import be.ambrosia.engine.map.elements.Wall
import be.ambrosia.engine.particles.Particle
import be.ambrosia.engine.particles.Particle3D
import be.ambrosia.getlost.Cst
import be.ambrosia.getlost.Ids
import be.ambrosia.getlost.Layers
import com.badlogic.ashley.core.Entity

object PlayerShot {

    val assMan: AssMan = AmbContext.cxt.inject()

    fun init(entity: Entity, posX: Float, posY: Float, dirX: Float, dirY: Float): Entity {
        val pos = ECSEngine.createComponent(PosComp::class.java)
        val dir = ECSEngine.createComponent(DirComp::class.java)
        val dim = ECSEngine.createComponent(DimComp::class.java)
        val time = ECSEngine.createComponent(TimeComp::class.java)
        val draw = ECSEngine.createComponent(Drawable2DComp::class.java)
        val emitter = ECSEngine.createComponent(ParticleEmitter::class.java)
        val collider = ECSEngine.createComponent(ColliderComp::class.java)

        collider.pushBack = false
        collider.tileElementColliding.add(MapElement.wall)
        collider.collidingTile = {entity, mapTile, mapElement ->
            if (mapElement is Wall)
                CollisionSystem.wallCollision(PosComp.mapper.get(entity), DirComp.mapper.get(entity), DimComp.mapper.get(entity), mapElement, mapTile)
        }
        collider.id = Ids.playerShot
        collider.collidingWith = Ids.cyclop

        dim.set(Cst.PlayerShot.w, Cst.PlayerShot.w)
        dir.setDir(dirX, dirY)
        dir.setDirLength(Cst.PlayerShot.speed)
        dir.setSpeed(Cst.PlayerShot.speed, Cst.PlayerShot.speed)

        pos.x = posX
        pos.y = posY
        pos.z = Layers.playerShot

        draw.batch = AmbContext.cxt.inject()
        draw.tr = assMan.textureRegions[Cst.PlayerShot.tr]

        val timer = Timer.obtain()
        timer.nextTrigger = Cst.PlayerShot.ttl
        timer.onTrigger = { entity ->
            ECSEngine.removeEntity(entity)
            true
        }
        time.timers.put("bardaf (ttl)", timer)

        entity.add(pos).add(dir).add(dim).add(time).add(draw).add(emitter).add(collider)
        return entity
    }

    fun emitParticle(): Particle {
        val p = Particle3D.pool.obtain()
        return p
    }

}
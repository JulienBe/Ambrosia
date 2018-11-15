package be.ambrosia.getlost.templates

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.Timer
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.ecs.systems.CollisionSystem
import be.ambrosia.engine.g.GColor
import be.ambrosia.engine.g.GRand
import be.ambrosia.engine.map.MapElement
import be.ambrosia.engine.map.elements.Wall
import be.ambrosia.engine.particles.Particle
import be.ambrosia.engine.particles.Particle3D
import be.ambrosia.getlost.Cst
import be.ambrosia.getlost.Ids
import be.ambrosia.getlost.Layers
import com.badlogic.ashley.core.Entity

object Energy {

    val assMan: AssMan = AmbContext.cxt.inject()
    val rand = AmbContext.cxt.inject<GRand>()

    fun init(entity: Entity, x: Float, y: Float): Entity {
        val pos = ECSEngine.createComponent(PosComp::class.java)
        val draw = ECSEngine.createComponent(Drawable2DComp::class.java)
        val time = ECSEngine.createComponent(TimeComp::class.java)
        val collider = ECSEngine.createComponent(ColliderComp::class.java)
        val sensed = ECSEngine.createComponent(CanBeSensedComp::class.java)

        collider.collidingWith = 0
        collider.id = Ids.energy
        sensed.id = Ids.energy

        collider.tileElementColliding.add(MapElement.wall)
        collider.collidingTile = {entity, mapTile, mapElement ->
            if (mapElement is Wall) {
                CollisionSystem.wallCollision(PosComp.mapper.get(entity), mapElement, mapTile)
            }
        }

        pos.set(Cst.Energy.w, Cst.Energy.w)

        pos.x = x
        pos.y = y
        pos.z = Layers.energy

        draw.batch = AmbContext.cxt.inject()
        draw.tr = assMan.textureRegions[Cst.Energy.tr]
        draw.color = GColor.convertARGB(1f, 0f, 1f, 0.5f + rand.float(0.25f))

        entity.add(pos).add(time).add(draw).add(collider).add(sensed)
        return entity
    }

    fun emitParticle(): Particle {
        val p = Particle3D.pool.obtain()
        return p
    }

}
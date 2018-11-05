package be.ambrosia.getlost.templates

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.Dimensions
import be.ambrosia.engine.Timer
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.ecs.systems.CollisionSystem
import be.ambrosia.engine.ecs.systems.CollisionSystem.Companion.wallCollision
import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.g.GColor
import be.ambrosia.engine.g.GRand
import be.ambrosia.engine.map.GameMap
import be.ambrosia.engine.map.MapElement
import be.ambrosia.engine.map.elements.Wall
import be.ambrosia.engine.particles.Particle
import be.ambrosia.engine.particles.Particle3D
import be.ambrosia.getlost.Ids
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute

object Cyclop {

    val assMan: AssMan = AmbContext.cxt.inject()
    val b: GBatch = AmbContext.cxt.inject()
    val b3d: ModelBatch = AmbContext.cxt.inject()
    val model = Drawable3DComp.modelBuilder.createBox(5f, 5f, 5f,
            Material(ColorAttribute.createDiffuse(Color.GREEN)),
            VertexAttributes.Usage.Position.toLong() or VertexAttributes.Usage.Normal.toLong())
    val width = 5f
    val hw = width / 2f
    val reproduceTimerKey = "repro"
    val reproduceTimerValue = 10f

    fun init(entity: Entity, x: Float, y: Float): Entity {
        val pos = ECSEngine.createComponent(PosComp::class.java)
        val dir = ECSEngine.createComponent(DirComp::class.java)
        val dim = ECSEngine.createComponent(DimComp::class.java)
        val time = ECSEngine.createComponent(TimeComp::class.java)
        val draw = ECSEngine.createComponent(Drawable2DComp::class.java)
        val emitter = ECSEngine.createComponent(ParticleEmitter::class.java)
        val wanderer = ECSEngine.createComponent(WandererComp::class.java)
        val body = ECSEngine.createComponent(BodyComp::class.java)
        val collider = ECSEngine.createComponent(ColliderComp::class.java)
        val id = ECSEngine.createComponent(IdComp::class.java)
        id.id = Ids.cyclop

        collider.tileElementColliding.add(MapElement.wall)
        collider.collidingTile = {entity, mapTile, mapElement ->
            if (mapElement is Wall) {
                CollisionSystem.wallCollision(
                        PosComp.mapper.get(entity), DirComp.mapper.get(entity), DimComp.mapper.get(entity), mapElement, mapTile
                )
            }
        }
        collider.colliding = ::colliding
        time.timers.put(reproduceTimerKey, Timer())
        wanderer.amplitude = 5f
        body.scale(width, width)
        dim.set(width, width)
        dir.setSpeed(0f, 100f)
        pos.x = x
        pos.y = y
        draw.batch = AmbContext.cxt.inject()
        draw.tr = assMan.textureRegions["debris"]
        draw.color = GColor.convertARGB(1f, 0.5f, 0.7f, 0.2f)
        entity.add(pos).add(dir).add(dim).add(time).add(draw).add(emitter).add(wanderer).add(body).add(collider).add(id)
        return entity
    }

    fun colliding(me: Entity, other: Entity) {
        if (IdComp.mapper.has(other) && IdComp.mapper.get(other).id != Ids.cyclop)
            return
        val pos = PosComp.mapper.get(me)
        val time = TimeComp.mapper.get(me)
        if (time.timers[reproduceTimerKey].current > reproduceTimerValue) {
            val cyclop = init(ECSEngine.createEntity(), pos.x + hw, pos.y + hw)
            ECSEngine.addEntity(cyclop)
            time.timers[reproduceTimerKey].reset()
        }
    }

    fun emitParticle(): Particle {
        val p = Particle3D.pool.obtain()
        return p
    }

}
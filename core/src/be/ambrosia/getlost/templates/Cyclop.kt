package be.ambrosia.getlost.templates

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.Timer
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.ecs.systems.CollisionSystem
import be.ambrosia.engine.g.GColor
import be.ambrosia.engine.map.MapElement
import be.ambrosia.engine.map.globalelems.Wall
import be.ambrosia.engine.particles.Particle
import be.ambrosia.engine.particles.Particle3D
import be.ambrosia.getlost.Cst
import be.ambrosia.getlost.Ids
import be.ambrosia.getlost.components.EnergyConsummerComp
import be.ambrosia.getlost.map.Energy
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2

object Cyclop {

    val assMan: AssMan = AmbContext.cxt.inject()
    var count = 0
    val reproduceTimerKey = "repro"
    var reproduceTimerValue = 15f

    fun init(entity: Entity, x: Float, y: Float): Entity {
        val pos = ECSEngine.createComponent(PosComp::class.java)
        val dir = ECSEngine.createComponent(DirComp::class.java)
        val time = ECSEngine.createComponent(TimeComp::class.java)
        val draw = ECSEngine.createComponent(Drawable2DComp::class.java)
        val emitter = ECSEngine.createComponent(ParticleEmitter::class.java)
        val wanderer = ECSEngine.createComponent(WandererComp::class.java)
        val body = ECSEngine.createComponent(BodyComp::class.java)
        val closeCollider = ECSEngine.createComponent(ColliderComp::class.java)
        val sensor = ECSEngine.createComponent(SensorComp::class.java)
        val energyConsummerComp = ECSEngine.createComponent(EnergyConsummerComp::class.java)

        closeCollider.collidingWith = Ids.player or Ids.cyclop or Ids.energy
        closeCollider.id = Ids.cyclop
        closeCollider.collidingWithTiles = MapElement.wall or Energy.energyId
        closeCollider.collidingTile = { entity, mapTile, mapElement ->
            when (mapElement) {
                is Wall -> CollisionSystem.wallCollision(PosComp.mapper.get(entity), DirComp.mapper.get(entity), mapElement, mapTile)
                is Energy -> {
                    val consummer = EnergyConsummerComp.mapper[entity]
                    consummer.energy += 5
                    mapElement.energy -= 5
                }
            }
        }
        closeCollider.colliding = ::mating

        sensor.circle.setRadius(Cst.Cyclop.sensorRadius)
        sensor.sensing = ::sensor
        sensor.sensingTiles = ::sensingTiles
        sensor.collidingWithTiles = Energy.energyId

        time.timers.put(reproduceTimerKey, Timer.obtain())
        wanderer.amplitude = Cst.Cyclop.wandererAmplitude
        body.scale(Cst.Cyclop.w, Cst.Cyclop.w)
        pos.set(Cst.Cyclop.w, Cst.Cyclop.w)
        dir.setSpeed(Cst.Cyclop.minSpeed, Cst.Cyclop.maxSpeed)

        pos.x = x
        pos.y = y

        draw.batch = AmbContext.cxt.inject()
        draw.tr = assMan.textureRegions[Cst.Cyclop.tr]
        draw.color = GColor.convertARGB(1f, 0.5f, 0.7f, 0.2f)

        entity.add(pos).add(dir).add(time).add(draw).add(emitter).add(wanderer).add(body).add(closeCollider).add(sensor).add(energyConsummerComp)
        count++
        return entity
    }

    fun sensingTiles(me: Entity, sensingTileElements: List<MapElement>) {
        if (sensingTileElements.isEmpty())
            return
        val mePos = PosComp.mapper.get(me)
        val target = sensingTileElements.filterIsInstance(Energy::class.java).sortedBy {
            it.energy / Vector2.dst2(mePos.x, mePos.y, it.tile.worldCenterX, it.tile.worldCenterY)
        }.last()
        val meDir = DirComp.mapper.get(me)
        CollisionSystem.goTowards(
                mePos,
                target.tile.worldCenterX,
                target.tile.worldCenterY,
                meDir,
                -Cst.Cyclop.sensorPush)
//            val dst = Vector2.dst(mePos.x, mePos.y, otherPos.x, otherPos.y)
        meDir.clampSpeed(0f, Cst.Cyclop.maxSpeed)
    }

    fun sensor(me: Entity, other: Entity) {
        if (CanBeSensedComp.mapper.get(other).id == Ids.energy) {
            val mePos = PosComp.mapper.get(me)
            val otherPos = PosComp.mapper.get(other)
            val meDir = DirComp.mapper.get(me)
            CollisionSystem.goTowards(
                    mePos,
                    otherPos.x + otherPos.hw,
                    otherPos.y + otherPos.hw,
                    meDir,
                    -Cst.Cyclop.sensorPush)
//            val dst = Vector2.dst(mePos.x, mePos.y, otherPos.x, otherPos.y)
            meDir.clampSpeed(0f, Cst.Cyclop.maxSpeed)
        }
    }

    fun mating(me: Entity, other: Entity) {
        if (ColliderComp.mapper.has(other) && ColliderComp.mapper.get(other).id != Ids.cyclop)
            return
        val pos = PosComp.mapper.get(me)
        val time = TimeComp.mapper.get(me)
        if (time.timers[reproduceTimerKey].current > reproduceTimerValue) {
            val cyclop = init(ECSEngine.createEntity(), pos.x + Cst.Cyclop.hw, pos.y + Cst.Cyclop.hw)
            ECSEngine.addEntity(cyclop)
            time.timers[reproduceTimerKey].reset()
        }
    }

    fun emitParticle(): Particle {
        val p = Particle3D.pool.obtain()
        return p
    }

}
package be.ambrosia.getlost.templates

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.Timer
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.ecs.systems.CollisionSystem
import be.ambrosia.engine.map.MapElement
import be.ambrosia.engine.map.globalelems.Wall
import be.ambrosia.engine.particles.Particle
import be.ambrosia.engine.particles.Particle3D
import be.ambrosia.getlost.Cst
import be.ambrosia.getlost.Ids
import be.ambrosia.getlost.components.EnergyConsummerComp
import be.ambrosia.getlost.map.Energy
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

object Cyclop {

    val assMan: AssMan = AmbContext.cxt.inject()
    val tr = assMan.textureRegions[Cst.Cyclop.tr]
    var count = 0
    val reproduceTimerKey = "repro"
    var reproduceTimerValue = 2f

    fun init(entity: Entity, x: Float, y: Float): Entity {
        val pos = ECSEngine.createComponent(PosComp::class.java, entity)
        val dir = ECSEngine.createComponent(DirComp::class.java, entity)
        val time = ECSEngine.createComponent(TimeComp::class.java, entity)
        val draw = ECSEngine.createComponent(Drawable2DComp::class.java, entity)
        val wanderer = ECSEngine.createComponent(WandererComp::class.java, entity)
        val body = ECSEngine.createComponent(BodyComp::class.java, entity)
        val closeCollider = ECSEngine.createComponent(ColliderComp::class.java, entity)
        val sensor = ECSEngine.createComponent(SensorComp::class.java, entity)
        val energyConsummerComp = ECSEngine.createComponent(EnergyConsummerComp::class.java, entity)

        closeCollider.collidingWith = Ids.player or Ids.cyclop or Ids.energy
        closeCollider.id = Ids.cyclop
        closeCollider.collidingWithTiles = MapElement.wall or Energy.energyId
        closeCollider.collidingTile = { entity, mapTile, mapElement ->
            when (mapElement) {
                is Wall -> CollisionSystem.wallCollision(PosComp.mapper.get(entity), DirComp.mapper.get(entity), mapElement, mapTile)
                is Energy -> {
                    val consummer = EnergyConsummerComp.mapper[entity]
                    if (mapElement.energy > 0) {
                        consummer.energy += 5
                        mapElement.energy -= 5
                    }
                }
            }
        }
        closeCollider.colliding = ::mating
        closeCollider.pushBounce = true
        closeCollider.pushBack = true

        val timer = Timer.obtain()
        timer.onTrigger = {
            if (energyConsummerComp.energy-- <= 0)
                ECSEngine.removeEntity(it)
            val size = size(energyConsummerComp.energy)
            pos.setDim(size, size)
            timer.nextTrigger += 0.1f
            true
        }
        timer.nextTrigger = 2f
        time.timers.put("energyDrain", timer)

        energyConsummerComp.energy = 200

        sensor.circle.setRadius(Cst.Cyclop.sensorRadius)
        sensor.sensing = ::sensor
        sensor.sensingTiles = ::sensingTiles
        sensor.collidingWithTiles = Energy.energyId

        time.timers.put(reproduceTimerKey, Timer.obtain())
        wanderer.amplitude = Cst.Cyclop.wandererAmplitude
        body.scale(Cst.Cyclop.w, Cst.Cyclop.w)
        pos.setDim(Cst.Cyclop.w, Cst.Cyclop.w)
        dir.setSpeed(Cst.Cyclop.minSpeed, Cst.Cyclop.maxSpeed)

        pos.x = x
        pos.y = y

        draw.draw = {
            it.setColor(MathUtils.clamp(energyConsummerComp.energy / 500f, 0f, 1f), 0.5f, 0.2f, 1f)
            it.draw(tr, pos.x, pos.y, pos.w, pos.h)
        }

        count++
        return entity
    }

    fun size(energy: Int): Float {
        return Cst.Cyclop.w + (Cst.Cyclop.w * (energy / 200f))
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
        meDir.clampSpeed()
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
        if (time.timers[reproduceTimerKey].current > reproduceTimerValue && EnergyConsummerComp.mapper[me].energy > 200) {
            val cyclop = init(ECSEngine.createEntity(), pos.x + Cst.Cyclop.hw, pos.y + Cst.Cyclop.hw)
            ECSEngine.addEntity(cyclop)
            time.timers[reproduceTimerKey].reset()
            EnergyConsummerComp.mapper[me].energy -= 200
        }
    }

    fun emitParticle(): Particle {
        val p = Particle3D.pool.obtain()
        return p
    }

}
package be.ambrosia.getlost.templates

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.Dimensions
import be.ambrosia.engine.Timer
import be.ambrosia.engine.state.GameState
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.ecs.systems.CollisionSystem
import be.ambrosia.engine.g.GColor
import be.ambrosia.engine.g.GInput
import be.ambrosia.engine.map.MapElement
import be.ambrosia.engine.map.globalelems.Wall
import be.ambrosia.engine.particles.Particle
import be.ambrosia.engine.particles.Particle3D
import be.ambrosia.engine.state.State
import be.ambrosia.getlost.Cst
import be.ambrosia.getlost.Ids
import be.ambrosia.getlost.Layers
import be.ambrosia.getlost.components.EnergyConsummerComp
import com.badlogic.ashley.core.Entity
import ktx.ashley.has

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
        val hp = ECSEngine.createComponent(HPComp::class.java, entity)
        val time = ECSEngine.createComponent(TimeComp::class.java, entity)

        time.player = true

        var canShoot = false
        val canShootTimer = Timer.obtain()
        canShootTimer.setOnTrigger {
            canShoot = true
            true
        }
        canShootTimer.nextTrigger = 0f
        time.timers.put("canShoot", canShootTimer)

        hp.hp = 5

        collider.pushBack = true
        collider.pushBounce = true
        collider.collidingWithTiles = MapElement.wall
        collider.collidingTile = {entity, mapTile, mapElement ->
            if (mapElement is Wall)
                CollisionSystem.wallCollideAndStick(pos, mapElement, dir)
        }
        collider.collidingWith = Ids.cyclop
        collider.id = Ids.player
        collider.colliding = { me, other ->
            hp.hp -= 1
            if (other.has(EnergyConsummerComp.mapper)) {
                EnergyConsummerComp.mapper[other].energy -= 100
            }
            if (hp.hp < 0) {
                ECSEngine.removeEntity(me)
                State.changeState(GameState.LOST)
            }
        }

        pos.setDim(Cst.Player.w, Cst.Player.w)
        pos.x = Dimensions.gameHW
        pos.y = Dimensions.gameHH
        dir.setSpeed(Cst.Player.speed, Cst.Player.speed)

        pos.z = Layers.player
        draw.draw = {
            it.setColor(color)
            it.draw(tr, pos.x, pos.y, pos.w, pos.h)
        }

        control.onClick = {
            if (canShoot) {
                ECSEngine.addEntity(
                        PlayerShot.init(ECSEngine.createEntity(),
                                pos.centerX, pos.centerY,
                                GInput.clicX() - pos.centerX, GInput.clicY() - pos.centerY)
                )
                canShootTimer.nextTrigger = canShootTimer.current + 0.2f
                canShoot = false
            }
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
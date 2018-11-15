package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.g.GBench
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import ktx.ashley.allOf

class SensorSystem : IteratingSystem(family) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        bench.begin()
        val pos = posMapper.get(entity)
        val canBeSensed = ECSEngine.getEntitiesFor(canBeSensed)
        val sensor = sensorMapper.get(entity)
        sensor.circle.setPosition(pos.x + pos.hw, pos.y + pos.hh)

        canBeSensed.forEach {
            val otherPos = posMapper.get(it)
            if (it != entity && (
                    sensor.circle.contains(otherPos.x, otherPos.y) ||
                    sensor.circle.contains(otherPos.x + otherPos.w, otherPos.y) ||
                    sensor.circle.contains(otherPos.x + otherPos.w, otherPos.y + otherPos.h) ||
                    sensor.circle.contains(otherPos.x, otherPos.y + otherPos.h))) {
                sensor.sensing(entity, it)
            }
        }
        bench.end()
    }

    companion object {
        val bench = GBench("sensor  ")
        val posMapper = PosComp.mapper
        val sensorMapper = SensorComp.mapper

        val family: Family = allOf(
                PosComp::class,
                SensorComp::class).get()
        val canBeSensed = allOf(
                CanBeSensedComp::class,
                PosComp::class).get()
    }
}

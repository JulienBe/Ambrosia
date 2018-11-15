package be.ambrosia.engine.ecs.systems

import be.ambrosia.engine.g.GBench
import be.ambrosia.engine.map.GameMap
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem

class TileBinderSystem : IteratingSystem(CollisionSystem.family)  {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        bench.begin()
        val pos = CollisionSystem.posMapper.get(entity)
        // TODO : be careful, it only takes each corner
        pos.tileSet.clear()
        // about 5% slower
//        posOffsets.forEach {
//            pos.tileSet.add(
//                    GameMap.getTileInWorldCoord(pos.x + pos.w * it.first, pos.y + pos.h * it.second).addEntity(entity)
//            )
//        }
        pos.tileSet.add(GameMap.getTileInWorldCoord(pos.x,          pos.y).addEntity(entity))
        pos.tileSet.add(GameMap.getTileInWorldCoord(pos.x + pos.w,  pos.y).addEntity(entity))
        pos.tileSet.add(GameMap.getTileInWorldCoord(pos.x + pos.w,  pos.y + pos.h).addEntity(entity))
        pos.tileSet.add(GameMap.getTileInWorldCoord(pos.x,          pos.y + pos.h).addEntity(entity))
        bench.end()
    }

    companion object {
        val bench = GBench("Tile Bind")
        val posOffsets = listOf<Pair<Float, Float>>(
                Pair(0f, 0f),
                Pair(1f, 0f),
                Pair(1f, 1f),
                Pair(0f, 1f)
        )
    }
}
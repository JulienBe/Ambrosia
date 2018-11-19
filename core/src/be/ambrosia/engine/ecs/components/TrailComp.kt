package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.g.GColor
import com.badlogic.ashley.core.Entity
import ktx.ashley.mapperFor

class TrailComp : TemplateComp {

    var lifetime = 10
    var draw: (GBatch, entity: Entity) -> Unit = { batch, e ->
        batch.setColor(defaultColor)
        val pos = PosComp.mapper[e]
        batch.draw(defaultTr, pos.x, pos.y, pos.w, pos.h)
    }

    companion object {
        val mapper = mapperFor<TrailComp>()
        val defaultTr = AmbContext.cxt.inject<AssMan>().textureRegions["debris"]
        val defaultColor = GColor.convertARGB(1f, 1f, 1f, 0f)
    }
}
package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.g.GBatch
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.ashley.mapperFor

class Drawable2DComp(var color: Float = Color.WHITE.toFloatBits()) : TemplateComp {
    var draw: (GBatch) -> Unit = {}
    companion object {
        val mapper = mapperFor<Drawable2DComp>()
    }
}
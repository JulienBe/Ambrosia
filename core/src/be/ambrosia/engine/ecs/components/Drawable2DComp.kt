package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.g.GBatch
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.ashley.mapperFor

class Drawable2DComp(var color: Float = Color.WHITE.toFloatBits()) : TemplateComp {
    lateinit var batch: GBatch
    lateinit var tr: TextureRegion
    companion object {
        val mapper = mapperFor<Drawable2DComp>()
    }
}
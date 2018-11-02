package be.ambrosia.engine.ecs.components

import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import ktx.ashley.mapperFor

class Drawable3DComp : TemplateComp {

    lateinit var instance: ModelInstance
    lateinit var batch: ModelBatch

    companion object {
        val mapper = mapperFor<Drawable3DComp>()
        val modelBuilder = ModelBuilder()
    }
}
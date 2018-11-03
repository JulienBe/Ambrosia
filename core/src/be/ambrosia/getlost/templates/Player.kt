package be.ambrosia.getlost.templates

import be.ambrosia.engine.AmbContext
import be.ambrosia.engine.AssMan
import be.ambrosia.engine.ecs.ECSEngine
import be.ambrosia.engine.ecs.components.*
import be.ambrosia.engine.g.GBatch
import be.ambrosia.engine.particles.Particle
import be.ambrosia.engine.particles.Particle3D
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute

object Player {

    val assMan: AssMan = AmbContext.cxt.inject()
    val b: GBatch = AmbContext.cxt.inject()
    val b3d: ModelBatch = AmbContext.cxt.inject()
    val model = Drawable3DComp.modelBuilder.createBox(5f, 5f, 5f,
            Material(ColorAttribute.createDiffuse(Color.GREEN)),
            VertexAttributes.Usage.Position.toLong() or VertexAttributes.Usage.Normal.toLong())

    fun init(entity: Entity): Entity {
        val pos = ECSEngine.createComponent(PosComp::class.java)
        val dir = ECSEngine.createComponent(DirComp::class.java)
        val dim = ECSEngine.createComponent(DimComp::class.java)
        val time = ECSEngine.createComponent(TimeComp::class.java)
        val draw = ECSEngine.createComponent(Drawable2DComp::class.java)
        val control = ECSEngine.createComponent(ControlComp::class.java)
        val emitter = ECSEngine.createComponent(ParticleEmitter::class.java)

        dim.set(5f, 5f)
        dir.setSpeed(200f, 200f)
        pos.z = 1f
        draw.batch = AmbContext.cxt.inject()
        draw.tr = assMan.textureRegions["debris"]
        entity.add(pos).add(dir).add(dim).add(time).add(draw).add(control).add(emitter)
        return entity
    }

    fun emitParticle(): Particle {
        val p = Particle3D.pool.obtain()
        return p
    }

}
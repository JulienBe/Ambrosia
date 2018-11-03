package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.AmbContext
import com.badlogic.gdx.physics.box2d.*
import ktx.ashley.mapperFor
import ktx.box2d.body

class BodyComp : TemplateComp {

    val body = world.body {
        box(1f, 1f)
        type = BodyDef.BodyType.StaticBody
    }

    fun scale(w: Float, h: Float) {
        body.destroyFixture(body.fixtureList.first())
        val shape = PolygonShape()
        shape.setAsBox(w, h)
        val fixtureDef = FixtureDef()
//        fixtureDef.isSensor = true
        fixtureDef.shape = shape
        body.createFixture(fixtureDef)
        shape.dispose()
    }

    companion object {
        val mapper = mapperFor<BodyComp>()
        val world = AmbContext.cxt.inject<World>()
    }
}
package be.ambrosia.engine

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold

class B2DListener : ContactListener {
    override fun endContact(contact: Contact?) {
        println("B2DListener.endContact")
    }

    override fun beginContact(contact: Contact?) {
        println("B2DListener.beginContact")
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
        println("B2DListener.preSolve")
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
        println("B2DListener.postSolve")
    }
}
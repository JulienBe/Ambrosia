package be.ambrosia.getlost

import be.ambrosia.engine.Dimensions

object Cst {
    object Player {
        var w = Dimensions.pixel * 15
        var speed = Dimensions.pixel * 200
        val tr = "debris"
    }
    object Cyclop {
        var w = Dimensions.pixel * 5
        var hw = w / 2f
        var wandererAmplitude = Dimensions.pixel * 5
        val minSpeed = 0f
        var maxSpeed = Player.speed
        val tr = "debris"
    }
    object PlayerShot {
        var w = Dimensions.pixel * 10
        var hw = w / 2f
        var speed = Dimensions.pixel * 800
        val tr = "debris"
        const val ttl = 3f
    }
}
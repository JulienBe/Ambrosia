package be.ambrosia.getlost

import be.ambrosia.engine.Dimensions

object Cst {
    object Player {
        var w = Dimensions.pixel * 15
        var speed = Dimensions.pixel * 200
        val tr = "debris"
    }
    object Cyclop {
        var w = Dimensions.pixel * 8
        var hw = w / 2f
        var wandererAmplitude = Dimensions.pixel * 5
        val minSpeed = 0f
        var maxSpeed = Player.speed / 4f
        val tr = "debris"
        val sensorRadius = 200f
        val sensorPush = 5f
    }
    object PlayerShot {
        var w = Dimensions.pixel * 10
        var hw = w / 2f
        var speed = Dimensions.pixel * 800
        val tr = "debris"
        const val ttl = 3f
    }
    object Energy {
        var w = Dimensions.pixel * 5
        var hw = w / 2f
        val tr = "debris"
    }
}
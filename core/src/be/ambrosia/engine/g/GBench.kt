package be.ambrosia.engine.g

import com.badlogic.gdx.Gdx

class GBench(val name: String) {

    var previousFrame = Gdx.graphics.frameId
    var frameTime = 0L
    var totalTime = 0L
    var currentIteration = 0L
    var iter = 0

    fun begin() {
        currentIteration = System.nanoTime()
    }

    fun end(msg: String = "") {
        if (previousFrame != Gdx.graphics.frameId) {
            totalTime += frameTime
            Gdx.app.log("gbench", "$name \t total :$totalTime \t frame: F${frameTime}F \t iter : $iter --- $msg")
            frameTime = 0L
            iter = 0
            previousFrame = Gdx.graphics.frameId
        } else {
            frameTime += System.nanoTime() - currentIteration
        }
    }
}
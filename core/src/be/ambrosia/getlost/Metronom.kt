package be.ambrosia.getlost

import com.badlogic.gdx.Gdx

class Metronom {
    private var mobTime = phaseTime
    private var playerTime = phaseTime
    var playerDelta = 0f
    var mobsDelta = 0f
    private val swapTime = Gdx.audio.newSound(Gdx.files.internal("swaptime.wav"))
    var playerPhase = true

    private fun changePhase() {
        swapTime.play()
        playerPhase = !playerPhase
        if (playerPhase) {
            mobTime = 0.1f
            playerTime = phaseTime
        } else {
            playerTime = 0.1f
            mobTime = phaseTime
        }
    }

    fun act() {
        playerDelta = Gdx.graphics.deltaTime
        mobsDelta = Gdx.graphics.deltaTime
        if (playerPhase) {
            playerDelta *= Math.min(playerTime, 1f)
            playerTime -= Gdx.graphics.deltaTime
            mobsDelta = 0f
        } else {
            mobsDelta *= Math.min(mobTime, 1f)
            mobTime -= Gdx.graphics.deltaTime
            playerDelta = 0f
        }
        if (playerTime < 0f || mobTime < 0f) {
            changePhase()
        }
    }

    companion object {
        private val phaseTime = 3f
    }
}

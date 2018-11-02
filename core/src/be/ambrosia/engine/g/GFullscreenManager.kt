package g

import com.badlogic.gdx.Gdx

object GFullscreenManager {
    fun setFullscreen(fullscreen: Boolean) {
        val modePrimary = Gdx.graphics.getDisplayMode(Gdx.graphics.monitors[0])
        if (fullscreen) {
            Gdx.graphics.setUndecorated(true)
            Gdx.graphics.setFullscreenMode(modePrimary)
        } else {
            Gdx.graphics.setUndecorated(false)
            Gdx.graphics.setWindowedMode(modePrimary.width, modePrimary.height)
        }
    }

    fun isFullscreen(): Boolean {
        return Gdx.graphics.isFullscreen
    }
}
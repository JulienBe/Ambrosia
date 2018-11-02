package be.ambrosia.engine.g

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Affine2

class GBatch(i: Int) : SpriteBatch(i) {

    var draws = 0
    var currentFrame = 0L

    fun draw(tr: TextureRegion, x: Int, y: Int, w: Float, h: Float) {
        draw(tr, x.toFloat(), y.toFloat(), w, h)
        count()
    }

    fun draw(tr: TextureRegion, x: Float, y: Float, w: Float, h: Float, angle: Float) {
        draw(tr, x, y, w / 2f, h / 2f, w, h, 1f, 1f, angle)
        count()
    }

    override fun draw(texture: Texture?, x: Float, y: Float, originX: Float, originY: Float, width: Float, height: Float, scaleX: Float, scaleY: Float, rotation: Float, srcX: Int, srcY: Int, srcWidth: Int, srcHeight: Int, flipX: Boolean, flipY: Boolean) {
        super.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY)
        count()
    }

    override fun draw(texture: Texture?, x: Float, y: Float, width: Float, height: Float, srcX: Int, srcY: Int, srcWidth: Int, srcHeight: Int, flipX: Boolean, flipY: Boolean) {
        super.draw(texture, x, y, width, height, srcX, srcY, srcWidth, srcHeight, flipX, flipY)
        count()
    }

    override fun draw(texture: Texture?, x: Float, y: Float, srcX: Int, srcY: Int, srcWidth: Int, srcHeight: Int) {
        super.draw(texture, x, y, srcX, srcY, srcWidth, srcHeight)
        count()
    }

    override fun draw(texture: Texture?, x: Float, y: Float, width: Float, height: Float, u: Float, v: Float, u2: Float, v2: Float) {
        super.draw(texture, x, y, width, height, u, v, u2, v2)
        count()
    }

    override fun draw(texture: Texture?, x: Float, y: Float) {
        super.draw(texture, x, y)
        count()
    }

    override fun draw(texture: Texture?, x: Float, y: Float, width: Float, height: Float) {
        super.draw(texture, x, y, width, height)
        count()
    }

    override fun draw(texture: Texture?, spriteVertices: FloatArray?, offset: Int, count: Int) {
        super.draw(texture, spriteVertices, offset, count)
        count()
    }

    override fun draw(region: TextureRegion?, x: Float, y: Float) {
        super.draw(region, x, y)
        count()
    }

    override fun draw(region: TextureRegion?, x: Float, y: Float, width: Float, height: Float) {
        super.draw(region, x, y, width, height)
        count()
    }

    override fun draw(region: TextureRegion?, x: Float, y: Float, originX: Float, originY: Float, width: Float, height: Float, scaleX: Float, scaleY: Float, rotation: Float) {
        super.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        count()
    }

    override fun draw(region: TextureRegion?, x: Float, y: Float, originX: Float, originY: Float, width: Float, height: Float, scaleX: Float, scaleY: Float, rotation: Float, clockwise: Boolean) {
        super.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation, clockwise)
        count()
    }

    override fun draw(region: TextureRegion?, width: Float, height: Float, transform: Affine2?) {
        super.draw(region, width, height, transform)
        count()
    }

    private fun count() {
//        if (currentFrame != Gdx.graphics.frameId) {
//            currentFrame = Gdx.graphics.frameId
//            println(draws)
//            draws = 0
//        }
//        draws++
    }

    fun setColor(fl: Float) {
        packedColor = fl
    }
}
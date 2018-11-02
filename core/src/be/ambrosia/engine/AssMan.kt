package be.ambrosia.engine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.GdxMap

class AssMan(val atlasName: String, val regionsList: List<String>) : AssetErrorListener {

    private var atlas: TextureAtlas? = null
    val manager = AssetManager()
    val textureRegions = GdxMap<String, TextureRegion>()

    init {
        manager.setErrorListener(this)
        Texture.setAssetManager(manager)
        setup()
    }

    fun setup() {
        while (!manager.update()) {
            println("Loading")
        }
        println("now loading ${regionsList.size} regions")
        regionsList.forEach {
            textureRegions.put(it, getAtlas().findRegion(it))
            println("initializing texture region $it : ${textureRegions.get(it)}")
        }
    }

    private fun getAtlas(): TextureAtlas {
        if (atlas == null)
            atlas = TextureAtlas(Gdx.files.internal(atlasName))
        return atlas!!
    }

    override fun error(asset: AssetDescriptor<*>?, throwable: Throwable?) {
        println("Could not load asset $asset")
        if (throwable != null)
            System.err.println(throwable.message)
    }
}
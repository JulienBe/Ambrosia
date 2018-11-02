package be.ambrosia.engine.g

import com.badlogic.gdx.utils.Array

class GArray<T>(ordered: Boolean = false, size: Int = 10) : Array<T>(ordered, 16) {

    fun get(x: Int, y: Int, dim: Int): T {
        return get((x * dim) + (y % dim))
    }

    fun get(x: Float, y: Float, dim: Int): T {
        return get((x.toInt() * dim) + (y.toInt() % dim))
    }

    fun a(value: T): GArray<T> {
        super.add(value)
        return this
    }
}
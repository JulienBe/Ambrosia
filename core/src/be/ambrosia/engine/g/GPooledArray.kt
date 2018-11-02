package g

import be.ambrosia.engine.g.GArray

abstract class GPooledArray<T>: GPooled<T>() {
    val array = GArray<T>()

    fun obtainNoAdd(): T {
        return pool.obtain()
    }

    override fun obtain(): T {
        val t = pool.obtain()
        array.add(t)
        return t
    }

    override fun free(t: T) {
        array.removeValue(t, true)
        pool.free(t)
    }

    fun clear() {
        pool.freeAll(array)
        array.clear()
    }

    fun onlyFree(it: T) {
        pool.free(it)
    }
}
package g

import com.badlogic.gdx.utils.Pool

abstract class GPooled<T> {
    protected val pool = object : Pool<T>() {
        override fun newObject(): T {
            return init()
        }
    }

    abstract fun init(): T

    open fun obtain(): T {
        return pool.obtain()
    }

    open fun free(t: T) {
        pool.free(t)
    }

}
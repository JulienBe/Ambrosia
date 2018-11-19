package be.ambrosia.engine

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool

class Timer private constructor() {
    var current = 0f
    var nextTrigger = 9999999999999999999999f
    var onTrigger: (entity: Entity) -> (Boolean) = {entity -> false}

    fun reset() {
        current = 0f
        nextTrigger = 9999999999999999999999f
    }

    fun free() {
        reset()
        pool.free(this)
    }

    fun setOnTrigger(function: (Entity) -> Boolean): Timer {
        onTrigger = function
        return this
    }

    companion object {
        private val pool = object : Pool<Timer>() {
            override fun newObject(): Timer {
                return Timer()
            }
        }
        fun obtain(): Timer {
            return pool.obtain()
        }
    }
}
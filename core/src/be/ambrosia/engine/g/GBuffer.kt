package be.ambrosia.engine.g

class GBuffer<T>(private val size: Int) {

    private val array: Array<T> = arrayOfNulls<Any>(size) as Array<T>
    private var next = 0
    val nextItem get() = array[next % size]

    fun add(t: T) {
        array[next++ % size] = t
    }

    fun getAndIterate(): T {
        return array[next++ % size]
    }

    operator fun get(index: Int): T {
        return array[(this.next - 1 + index) % size]
    }

    fun fill(function: () -> T): GBuffer<T> {
        array.forEachIndexed { index, _ ->
            array[index] = function()
        }
        return this
    }
}

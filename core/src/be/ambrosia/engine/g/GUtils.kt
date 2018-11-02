package be.ambrosia.engine.g

object GUtils {
    fun clamp(f: Float, min: Float, max: Float): Float {
        if (f > max)
            return max
        if (f < min)
            return min
        return f
    }
    fun clamp(f: Int, min: Int, max: Int): Int {
        if (f > max)
            return max
        if (f < min)
            return min
        return f
    }
}
package be.ambrosia.engine.g

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

object GArrayManipulator {
    @JvmStatic
    fun convert(tmp: Array<Float>): FloatArray {
        val array2 = FloatArray(tmp.size)
        for ((i, f) in tmp.withIndex())
            array2[i] = f!!.toFloat()
        return array2
    }

    @JvmStatic
    fun getDifferences(array: FloatArray): FloatArray {
        val tmp = Array<Float>()
        tmp.add(0f)
        for (i in 1 until array.size)
            tmp.add((array[i] - array[i - 1]) / 2)
        return convert(tmp)
    }

    @JvmStatic
    fun convert(positions: Array<Vector2>): Array<Vector2> {
        val array2 = Array<Vector2>(positions.size)
        for ((i, f) in positions.withIndex())
            array2[i] = f
        return array2
    }

    @JvmStatic
    fun getDouble(array: FloatArray): FloatArray {
        val tmp = Array<Float>()
        tmp.add(0f)
        for (i in 1 until array.size)
            tmp.add(array[i] * 2)
        return convert(tmp)
    }

}

package be.ambrosia.engine.state

import ktx.collections.GdxArray

object State {

    var current = GameState.WAITING
    val onChange = GdxArray<(GameState) -> Unit>()

    fun changeState(new: GameState) {
        onChange.forEach {
            it.invoke(new)
        }
        current = new
    }

    fun register(onStateChange: (GameState) -> Unit) {
        onChange.add(onStateChange)
    }

}
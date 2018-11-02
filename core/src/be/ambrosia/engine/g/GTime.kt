package be.ambrosia.engine.g

object GTime {
    var delta = 0f
    var playerDelta = 0f
    var playerTime = 0f
    var time = 0f
    var delta2 = 0f
    var delta4 = 0f
    var delta15 = 0f
    var delta25 = 0f
    var delta100 = 0f
    var delta300 = 0f
    var deltaDiv3 = 0f
    var deltaDiv2 = 0f

    var oneToFour = 1
    var oneToHeight = 1
    var to20 = 1
    var to50 = 0
    var to150 = 0

    var alternate = true

    fun stopTime() {
        delta = 0f
    }

    fun reset() {
        time = 0f
        playerTime = 0f
    }

    fun majDeltas(delta: Float, playerDelta: Float) {
        GTime.playerDelta = playerDelta
        playerTime += playerDelta
        time += delta
        GTime.delta = delta

        delta100 = delta * 100f
        delta300 = delta * 300f
        delta15 = delta * 15
        deltaDiv3 = delta / 3
        deltaDiv2 = delta / 2
        delta2 = delta * 2
        delta4 = delta * 4
        delta25 = delta * 25
    }

    fun alternate() {
        alternate = !alternate
        if (++oneToFour > 4)
            oneToFour = 1
        if (++oneToHeight > 8)
            oneToHeight = 1
        if (++to20 > 20)
            to20 = 1
        if (++to50 > 50)
            to50 = 1
        if (++to150 > 150)
            to150 = 1
    }

}
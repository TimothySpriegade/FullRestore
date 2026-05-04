package dev.timothyspriegade.fullrestore.domain.model

enum class Effectiveness(val multiplier: Double, val label: String) {
    FOUR_X(4.0, "4x"),
    TWO_X(2.0, "2x"),
    NEUTRAL(1.0, "1x"),
    HALF_X(0.5, "0.5x"),
    QUARTER_X(0.25, "0.25x"),
    IMMUNE(0.0, "Immune");

    companion object {
        fun fromMultiplier(multiplier: Double): Effectiveness = when (multiplier) {
            4.0 -> FOUR_X
            2.0 -> TWO_X
            1.0 -> NEUTRAL
            0.5 -> HALF_X
            0.25 -> QUARTER_X
            0.0 -> IMMUNE
            else -> NEUTRAL
        }

        val ordered: List<Effectiveness> = listOf(
            FOUR_X,
            TWO_X,
            NEUTRAL,
            HALF_X,
            QUARTER_X,
            IMMUNE
        )
    }
}

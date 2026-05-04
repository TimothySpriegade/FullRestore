package dev.timothyspriegade.fullrestore.core.util

import dev.timothyspriegade.fullrestore.domain.model.PokemonType

object TypeChart {
    private val chart: Map<PokemonType, Map<PokemonType, Double>> = mapOf(
        PokemonType.NORMAL to mapOf(
            PokemonType.ROCK to 0.5,
            PokemonType.STEEL to 0.5,
            PokemonType.GHOST to 0.0
        ),
        PokemonType.FIRE to mapOf(
            PokemonType.GRASS to 2.0,
            PokemonType.ICE to 2.0,
            PokemonType.BUG to 2.0,
            PokemonType.STEEL to 2.0,
            PokemonType.FIRE to 0.5,
            PokemonType.WATER to 0.5,
            PokemonType.ROCK to 0.5,
            PokemonType.DRAGON to 0.5
        ),
        PokemonType.WATER to mapOf(
            PokemonType.FIRE to 2.0,
            PokemonType.GROUND to 2.0,
            PokemonType.ROCK to 2.0,
            PokemonType.WATER to 0.5,
            PokemonType.GRASS to 0.5,
            PokemonType.DRAGON to 0.5
        ),
        PokemonType.ELECTRIC to mapOf(
            PokemonType.WATER to 2.0,
            PokemonType.FLYING to 2.0,
            PokemonType.ELECTRIC to 0.5,
            PokemonType.GRASS to 0.5,
            PokemonType.DRAGON to 0.5,
            PokemonType.GROUND to 0.0
        ),
        PokemonType.GRASS to mapOf(
            PokemonType.WATER to 2.0,
            PokemonType.GROUND to 2.0,
            PokemonType.ROCK to 2.0,
            PokemonType.FIRE to 0.5,
            PokemonType.GRASS to 0.5,
            PokemonType.POISON to 0.5,
            PokemonType.FLYING to 0.5,
            PokemonType.BUG to 0.5,
            PokemonType.DRAGON to 0.5,
            PokemonType.STEEL to 0.5
        ),
        PokemonType.ICE to mapOf(
            PokemonType.GRASS to 2.0,
            PokemonType.GROUND to 2.0,
            PokemonType.FLYING to 2.0,
            PokemonType.DRAGON to 2.0,
            PokemonType.FIRE to 0.5,
            PokemonType.WATER to 0.5,
            PokemonType.ICE to 0.5,
            PokemonType.STEEL to 0.5
        ),
        PokemonType.FIGHTING to mapOf(
            PokemonType.NORMAL to 2.0,
            PokemonType.ICE to 2.0,
            PokemonType.ROCK to 2.0,
            PokemonType.DARK to 2.0,
            PokemonType.STEEL to 2.0,
            PokemonType.POISON to 0.5,
            PokemonType.FLYING to 0.5,
            PokemonType.PSYCHIC to 0.5,
            PokemonType.BUG to 0.5,
            PokemonType.FAIRY to 0.5,
            PokemonType.GHOST to 0.0
        ),
        PokemonType.POISON to mapOf(
            PokemonType.GRASS to 2.0,
            PokemonType.FAIRY to 2.0,
            PokemonType.POISON to 0.5,
            PokemonType.GROUND to 0.5,
            PokemonType.ROCK to 0.5,
            PokemonType.GHOST to 0.5,
            PokemonType.STEEL to 0.0
        ),
        PokemonType.GROUND to mapOf(
            PokemonType.FIRE to 2.0,
            PokemonType.ELECTRIC to 2.0,
            PokemonType.POISON to 2.0,
            PokemonType.ROCK to 2.0,
            PokemonType.STEEL to 2.0,
            PokemonType.GRASS to 0.5,
            PokemonType.BUG to 0.5,
            PokemonType.FLYING to 0.0
        ),
        PokemonType.FLYING to mapOf(
            PokemonType.GRASS to 2.0,
            PokemonType.FIGHTING to 2.0,
            PokemonType.BUG to 2.0,
            PokemonType.ELECTRIC to 0.5,
            PokemonType.ROCK to 0.5,
            PokemonType.STEEL to 0.5
        ),
        PokemonType.PSYCHIC to mapOf(
            PokemonType.FIGHTING to 2.0,
            PokemonType.POISON to 2.0,
            PokemonType.PSYCHIC to 0.5,
            PokemonType.STEEL to 0.5,
            PokemonType.DARK to 0.0
        ),
        PokemonType.BUG to mapOf(
            PokemonType.GRASS to 2.0,
            PokemonType.PSYCHIC to 2.0,
            PokemonType.DARK to 2.0,
            PokemonType.FIRE to 0.5,
            PokemonType.FIGHTING to 0.5,
            PokemonType.POISON to 0.5,
            PokemonType.FLYING to 0.5,
            PokemonType.GHOST to 0.5,
            PokemonType.STEEL to 0.5,
            PokemonType.FAIRY to 0.5
        ),
        PokemonType.ROCK to mapOf(
            PokemonType.FIRE to 2.0,
            PokemonType.ICE to 2.0,
            PokemonType.FLYING to 2.0,
            PokemonType.BUG to 2.0,
            PokemonType.FIGHTING to 0.5,
            PokemonType.GROUND to 0.5,
            PokemonType.STEEL to 0.5
        ),
        PokemonType.GHOST to mapOf(
            PokemonType.PSYCHIC to 2.0,
            PokemonType.GHOST to 2.0,
            PokemonType.DARK to 0.5,
            PokemonType.NORMAL to 0.0
        ),
        PokemonType.DRAGON to mapOf(
            PokemonType.DRAGON to 2.0,
            PokemonType.STEEL to 0.5,
            PokemonType.FAIRY to 0.0
        ),
        PokemonType.DARK to mapOf(
            PokemonType.PSYCHIC to 2.0,
            PokemonType.GHOST to 2.0,
            PokemonType.FIGHTING to 0.5,
            PokemonType.DARK to 0.5,
            PokemonType.FAIRY to 0.5
        ),
        PokemonType.STEEL to mapOf(
            PokemonType.ICE to 2.0,
            PokemonType.ROCK to 2.0,
            PokemonType.FAIRY to 2.0,
            PokemonType.FIRE to 0.5,
            PokemonType.WATER to 0.5,
            PokemonType.ELECTRIC to 0.5,
            PokemonType.STEEL to 0.5
        ),
        PokemonType.FAIRY to mapOf(
            PokemonType.FIGHTING to 2.0,
            PokemonType.DRAGON to 2.0,
            PokemonType.DARK to 2.0,
            PokemonType.FIRE to 0.5,
            PokemonType.POISON to 0.5,
            PokemonType.STEEL to 0.5
        )
    )

    fun effectiveness(attacker: PokemonType, defender: PokemonType): Double =
        chart[attacker]?.get(defender) ?: 1.0
}

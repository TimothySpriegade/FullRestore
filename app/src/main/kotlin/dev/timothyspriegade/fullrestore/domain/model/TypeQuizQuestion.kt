package dev.timothyspriegade.fullrestore.domain.model

data class TypeQuizQuestion(
    val attacker: PokemonType,
    val defenderPrimary: PokemonType,
    val defenderSecondary: PokemonType? = null
)

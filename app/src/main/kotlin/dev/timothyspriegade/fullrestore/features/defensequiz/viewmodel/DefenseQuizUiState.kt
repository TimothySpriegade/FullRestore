package dev.timothyspriegade.fullrestore.features.defensequiz.viewmodel

import dev.timothyspriegade.fullrestore.domain.model.Effectiveness
import dev.timothyspriegade.fullrestore.domain.model.PokemonType

data class DefenseQuizUiState(
    val defenderPrimary: PokemonType? = null,
    val defenderSecondary: PokemonType? = null,
    val assignments: Map<PokemonType, Effectiveness?> = PokemonType.entries.associateWith { null },
    val correctAssignments: Map<PokemonType, Effectiveness>? = null,
    val validationResults: Map<PokemonType, Boolean>? = null,
    val isSubmitted: Boolean = false,
    val streak: Int = 0,
    val selectedType: PokemonType? = null
)

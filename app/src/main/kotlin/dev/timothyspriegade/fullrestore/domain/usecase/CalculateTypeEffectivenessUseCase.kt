package dev.timothyspriegade.fullrestore.domain.usecase

import dev.timothyspriegade.fullrestore.core.util.TypeChart
import dev.timothyspriegade.fullrestore.domain.model.Effectiveness
import dev.timothyspriegade.fullrestore.domain.model.PokemonType

class CalculateTypeEffectivenessUseCase {
    fun execute(attacker: PokemonType, defender: PokemonType, secondary: PokemonType?): Effectiveness {
        val primaryMultiplier = TypeChart.effectiveness(attacker, defender)
        val secondaryMultiplier = secondary?.let { TypeChart.effectiveness(attacker, it) } ?: 1.0
        val total = primaryMultiplier * secondaryMultiplier
        return Effectiveness.fromMultiplier(total)
    }
}

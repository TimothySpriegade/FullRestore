package dev.timothyspriegade.fullrestore.domain.usecase

import dev.timothyspriegade.fullrestore.domain.model.PokemonType
import kotlin.random.Random

class GenerateDefenseQuizQuestionUseCase(
    private val random: Random = Random.Default
) {
    fun execute(): Pair<PokemonType, PokemonType?> {
        val types = PokemonType.entries
        val defenderPrimary = types[random.nextInt(types.size)]
        val useDual = random.nextBoolean()
        val defenderSecondary = if (useDual) {
            var secondary = types[random.nextInt(types.size)]
            while (secondary == defenderPrimary) {
                secondary = types[random.nextInt(types.size)]
            }
            secondary
        } else {
            null
        }
        return Pair(defenderPrimary, defenderSecondary)
    }
}

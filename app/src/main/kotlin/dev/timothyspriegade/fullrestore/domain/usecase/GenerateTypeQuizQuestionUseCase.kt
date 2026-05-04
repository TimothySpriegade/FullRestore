package dev.timothyspriegade.fullrestore.domain.usecase

import dev.timothyspriegade.fullrestore.domain.model.PokemonType
import dev.timothyspriegade.fullrestore.domain.model.QuizMode
import dev.timothyspriegade.fullrestore.domain.model.TypeQuizQuestion
import kotlin.random.Random

class GenerateTypeQuizQuestionUseCase(
    private val random: Random = Random.Default
) {
    fun execute(mode: QuizMode): TypeQuizQuestion {
        val types = PokemonType.entries
        val attacker = types[random.nextInt(types.size)]
        val defenderPrimary = types[random.nextInt(types.size)]
        val defenderSecondary = if (mode == QuizMode.DUAL_TYPE) {
            var secondary = types[random.nextInt(types.size)]
            while (secondary == defenderPrimary) {
                secondary = types[random.nextInt(types.size)]
            }
            secondary
        } else {
            null
        }
        return TypeQuizQuestion(
            attacker = attacker,
            defenderPrimary = defenderPrimary,
            defenderSecondary = defenderSecondary
        )
    }
}

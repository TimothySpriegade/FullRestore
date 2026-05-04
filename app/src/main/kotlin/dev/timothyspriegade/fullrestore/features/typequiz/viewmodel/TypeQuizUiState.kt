package dev.timothyspriegade.fullrestore.features.typequiz.viewmodel

import dev.timothyspriegade.fullrestore.domain.model.Effectiveness
import dev.timothyspriegade.fullrestore.domain.model.QuizMode
import dev.timothyspriegade.fullrestore.domain.model.TypeQuizQuestion

data class TypeQuizUiState(
    val mode: QuizMode = QuizMode.SINGLE_TYPE,
    val question: TypeQuizQuestion? = null,
    val selectedAnswer: Effectiveness? = null,
    val isCorrect: Boolean? = null,
    val correctAnswer: Effectiveness? = null,
    val streak: Int = 0
)

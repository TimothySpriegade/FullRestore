package dev.timothyspriegade.fullrestore.features.typequiz.viewmodel

import androidx.lifecycle.ViewModel
import dev.timothyspriegade.fullrestore.domain.model.Effectiveness
import dev.timothyspriegade.fullrestore.domain.model.QuizMode
import dev.timothyspriegade.fullrestore.domain.usecase.CalculateTypeEffectivenessUseCase
import dev.timothyspriegade.fullrestore.domain.usecase.GenerateTypeQuizQuestionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TypeQuizViewModel(
    private val generateQuestionUseCase: GenerateTypeQuizQuestionUseCase = GenerateTypeQuizQuestionUseCase(),
    private val calculateEffectivenessUseCase: CalculateTypeEffectivenessUseCase = CalculateTypeEffectivenessUseCase()
) : ViewModel() {

    private val _state = MutableStateFlow(TypeQuizUiState())
    val state: StateFlow<TypeQuizUiState> = _state

    init {
        nextQuestion()
    }

    fun updateMode(mode: QuizMode) {
        _state.update { it.copy(mode = mode, streak = 0) }
        nextQuestion()
    }

    fun selectAnswer(answer: Effectiveness) {
        val question = _state.value.question ?: return
        val correct = calculateEffectivenessUseCase.execute(
            attacker = question.attacker,
            defender = question.defenderPrimary,
            secondary = question.defenderSecondary
        )
        val isCorrect = correct == answer
        val newStreak = if (isCorrect) _state.value.streak + 1 else 0
        _state.update {
            it.copy(
                selectedAnswer = answer,
                isCorrect = isCorrect,
                correctAnswer = correct,
                streak = newStreak,
                answeredAtMillis = System.currentTimeMillis()
            )
        }
    }

    fun nextQuestion() {
        val question = generateQuestionUseCase.execute(_state.value.mode)
        _state.update {
            it.copy(
                question = question,
                selectedAnswer = null,
                isCorrect = null,
                correctAnswer = null,
                answeredAtMillis = null
            )
        }
    }
}

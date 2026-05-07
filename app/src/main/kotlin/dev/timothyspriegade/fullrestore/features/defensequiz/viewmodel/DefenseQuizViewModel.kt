package dev.timothyspriegade.fullrestore.features.defensequiz.viewmodel

import androidx.lifecycle.ViewModel
import dev.timothyspriegade.fullrestore.domain.model.Effectiveness
import dev.timothyspriegade.fullrestore.domain.model.PokemonType
import dev.timothyspriegade.fullrestore.domain.usecase.CalculateTypeEffectivenessUseCase
import dev.timothyspriegade.fullrestore.domain.usecase.GenerateDefenseQuizQuestionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class DefenseQuizViewModel(
    private val generateQuestionUseCase: GenerateDefenseQuizQuestionUseCase = GenerateDefenseQuizQuestionUseCase(),
    private val calculateEffectivenessUseCase: CalculateTypeEffectivenessUseCase = CalculateTypeEffectivenessUseCase()
) : ViewModel() {

    private val _state = MutableStateFlow(DefenseQuizUiState())
    val state: StateFlow<DefenseQuizUiState> = _state

    init {
        nextQuestion()
    }

    fun selectType(type: PokemonType?) {
        if (_state.value.isSubmitted) return
        _state.update { it.copy(selectedType = type) }
    }

    fun assignType(type: PokemonType, effectiveness: Effectiveness?) {
        if (_state.value.isSubmitted) return
        _state.update {
            it.copy(
                assignments = it.assignments + (type to effectiveness),
                selectedType = if (it.selectedType == type) null else it.selectedType
            )
        }
    }

    fun assignSelectedToBucket(effectiveness: Effectiveness?) {
        val selected = _state.value.selectedType ?: return
        assignType(selected, effectiveness)
    }

    fun submit() {
        val state = _state.value
        if (state.isSubmitted) return

        val results = mutableMapOf<PokemonType, Boolean>()
        var allCorrect = true

        PokemonType.entries.forEach { attacker ->
            val correct = calculateEffectivenessUseCase.execute(
                attacker = attacker,
                defender = state.defenderPrimary!!,
                secondary = state.defenderSecondary
            )
            val assigned = state.assignments[attacker]
            val isCorrect = correct == assigned
            results[attacker] = isCorrect
            if (!isCorrect) allCorrect = false
        }

        _state.update {
            it.copy(
                validationResults = results,
                isSubmitted = true,
                streak = if (allCorrect) it.streak + 1 else 0
            )
        }
    }

    fun nextQuestion() {
        val (rawPrimary, rawSecondary) = generateQuestionUseCase.execute()
        
        // Normalize: Ensure if only one type exists, it is always the primary one.
        // This prevents cases where only a secondary type might be present.
        val (primary, secondary) = if (rawPrimary == null && rawSecondary != null) {
            rawSecondary to null
        } else {
            rawPrimary to rawSecondary
        }

        _state.update {
            it.copy(
                defenderPrimary = primary,
                defenderSecondary = secondary,
                assignments = PokemonType.entries.associateWith { null },
                validationResults = null,
                isSubmitted = false,
                selectedType = null
            )
        }
    }
}

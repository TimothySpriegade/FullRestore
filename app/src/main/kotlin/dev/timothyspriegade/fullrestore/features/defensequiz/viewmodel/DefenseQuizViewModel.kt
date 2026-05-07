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

    fun assignAllUnassignedToNeutral() {
        if (_state.value.isSubmitted) return
        _state.update { state ->
            val newAssignments = state.assignments.toMutableMap()
            PokemonType.entries.forEach { type ->
                if (newAssignments[type] == null) {
                    newAssignments[type] = Effectiveness.NEUTRAL
                }
            }
            state.copy(
                assignments = newAssignments,
                selectedType = null
            )
        }
    }

    fun submit() {
        val state = _state.value
        if (state.isSubmitted) return

        val results = mutableMapOf<PokemonType, Boolean>()
        val correctMap = mutableMapOf<PokemonType, Effectiveness>()
        var allCorrect = true

        PokemonType.entries.forEach { attacker ->
            val correct = calculateEffectivenessUseCase.execute(
                attacker = attacker,
                defender = state.defenderPrimary!!,
                secondary = state.defenderSecondary
            )
            correctMap[attacker] = correct
            val assigned = state.assignments[attacker]
            val isCorrect = correct == assigned
            results[attacker] = isCorrect
            if (!isCorrect) allCorrect = false
        }

        _state.update {
            it.copy(
                validationResults = results,
                correctAssignments = correctMap,
                isSubmitted = true,
                streak = if (allCorrect) it.streak + 1 else 0
            )
        }
    }

    fun nextQuestion() {
        val (rawPrimary, rawSecondary) = generateQuestionUseCase.execute()
        val (primary, secondary) = rawPrimary to rawSecondary

        _state.update {
            it.copy(
                defenderPrimary = primary,
                defenderSecondary = secondary,
                assignments = PokemonType.entries.associateWith { null },
                correctAssignments = null,
                validationResults = null,
                isSubmitted = false,
                selectedType = null
            )
        }
    }
}

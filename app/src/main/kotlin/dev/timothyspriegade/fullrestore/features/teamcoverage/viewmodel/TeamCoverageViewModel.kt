package dev.timothyspriegade.fullrestore.features.teamcoverage.viewmodel

import androidx.lifecycle.ViewModel
import dev.timothyspriegade.fullrestore.domain.model.PokemonType
import dev.timothyspriegade.fullrestore.domain.model.TeamTypeSlot
import dev.timothyspriegade.fullrestore.domain.usecase.CalculateTeamCoverageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TeamCoverageViewModel(
    private val calculateTeamCoverageUseCase: CalculateTeamCoverageUseCase = CalculateTeamCoverageUseCase()
) : ViewModel() {
    private val _state = MutableStateFlow(TeamCoverageUiState())
    val state: StateFlow<TeamCoverageUiState> = _state

    fun updateSlot(index: Int, primary: PokemonType?, secondary: PokemonType?) {
        _state.update { current ->
            val updatedSlots = current.slots.toMutableList()
            if (index in updatedSlots.indices) {
                val normalizedPrimary = when {
                    primary != null -> primary
                    secondary != null -> secondary
                    else -> null
                }
                val normalizedSecondary =
                    if (normalizedPrimary != null && normalizedPrimary == secondary) {
                        null
                    } else {
                        secondary
                    }
                updatedSlots[index] =
                    TeamTypeSlot(primary = normalizedPrimary, secondary = normalizedSecondary)
            }
            val filtered = updatedSlots.filter { it.primary != null }
            val result = if (filtered.isNotEmpty()) {
                calculateTeamCoverageUseCase.execute(filtered)
            } else {
                null
            }
            current.copy(slots = updatedSlots, result = result)
        }
    }

    fun clearTeam() {
        _state.update {
            it.copy(slots = List(6) { TeamTypeSlot() }, result = null)
        }
    }
}

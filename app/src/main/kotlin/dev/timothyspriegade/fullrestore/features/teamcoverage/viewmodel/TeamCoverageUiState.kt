package dev.timothyspriegade.fullrestore.features.teamcoverage.viewmodel

import dev.timothyspriegade.fullrestore.domain.model.TeamCoverageResult
import dev.timothyspriegade.fullrestore.domain.model.TeamTypeSlot

data class TeamCoverageUiState(
    val slots: List<TeamTypeSlot> = List(6) { TeamTypeSlot() },
    val result: TeamCoverageResult? = null
)

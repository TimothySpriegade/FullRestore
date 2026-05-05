package dev.timothyspriegade.fullrestore.domain.usecase

import dev.timothyspriegade.fullrestore.core.util.TypeChart
import dev.timothyspriegade.fullrestore.domain.model.Effectiveness
import dev.timothyspriegade.fullrestore.domain.model.PokemonType
import dev.timothyspriegade.fullrestore.domain.model.TeamCoverageResult
import dev.timothyspriegade.fullrestore.domain.model.TeamTypeSlot

class CalculateTeamCoverageUseCase {
    fun execute(team: List<TeamTypeSlot>): TeamCoverageResult {
        val defenders = PokemonType.entries
        val offensiveMap = Effectiveness.ordered.associateWith { mutableListOf<PokemonType>() }
        val defensiveMap = Effectiveness.ordered.associateWith { mutableListOf<PokemonType>() }
        val weaknessCounts = mutableMapOf<PokemonType, Int>()

        defenders.forEach { defender ->
            val offenseMultiplier = team
                .filter { it.primary != null }
                .maxOfOrNull { slot ->
                    val primary = slot.primary ?: return@maxOfOrNull 0.0
                    val primaryMultiplier = TypeChart.effectiveness(primary, defender)
                    val secondaryMultiplier =
                        slot.secondary?.let { TypeChart.effectiveness(it, defender) }
                    if (secondaryMultiplier != null) {
                        maxOf(primaryMultiplier, secondaryMultiplier)
                    } else {
                        primaryMultiplier
                    }
                } ?: 1.0
            offensiveMap[Effectiveness.fromMultiplier(offenseMultiplier)]?.add(defender)

            val individualMultipliers = team
                .filter { it.primary != null }
                .map { slot ->
                    val primary = slot.primary ?: return@map 1.0
                    val primaryMultiplier = TypeChart.effectiveness(defender, primary)
                    val secondaryMultiplier =
                        slot.secondary?.let { TypeChart.effectiveness(defender, it) } ?: 1.0
                    primaryMultiplier * secondaryMultiplier
                }

            val defenseMultiplier = individualMultipliers.maxOrNull() ?: 1.0
            defensiveMap[Effectiveness.fromMultiplier(defenseMultiplier)]?.add(defender)

            val weakCount = individualMultipliers.count { it > 1.0 }
            if (weakCount > 0) {
                weaknessCounts[defender] = weakCount
            }
        }

        val offensive = Effectiveness.ordered.associateWith { key ->
            offensiveMap[key].orEmpty().sortedBy { it.displayName }
        }
        val defensive = Effectiveness.ordered.associateWith { key ->
            defensiveMap[key].orEmpty().sortedBy { it.displayName }
        }

        return TeamCoverageResult(
            offensive = offensive,
            defensive = defensive,
            weaknessCounts = weaknessCounts
        )
    }
}

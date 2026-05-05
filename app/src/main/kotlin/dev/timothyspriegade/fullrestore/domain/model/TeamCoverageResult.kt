package dev.timothyspriegade.fullrestore.domain.model

data class TeamCoverageResult(
    val offensive: Map<Effectiveness, List<PokemonType>>,
    val defensive: Map<Effectiveness, List<PokemonType>>,
    val weaknessCounts: Map<PokemonType, Int>
)

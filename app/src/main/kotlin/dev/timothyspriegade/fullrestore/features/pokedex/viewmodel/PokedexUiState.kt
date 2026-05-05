package dev.timothyspriegade.fullrestore.features.pokedex.viewmodel

import dev.timothyspriegade.fullrestore.data.local.pokemon.PokemonEntity

data class PokedexUiState(
    val isLoading: Boolean = true,
    val pokemon: List<PokemonEntity> = emptyList()
)

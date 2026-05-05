package dev.timothyspriegade.fullrestore.features.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.timothyspriegade.fullrestore.data.local.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokedexViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(PokedexUiState())
    val state: StateFlow<PokedexUiState> = _state

    init {
        loadPokemon()
    }

    private fun loadPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            val dao = AppDatabase.getDatabase(getApplication()).pokemonDao()
            var pokemonList = dao.getAllPokemon()
            if (pokemonList.isEmpty()) {
                var attempts = 0
                while (attempts < MAX_LOAD_ATTEMPTS && pokemonList.isEmpty()) {
                    attempts += 1
                    delay(LOAD_RETRY_DELAY_MS)
                    pokemonList = dao.getAllPokemon()
                }
            }
            _state.value = PokedexUiState(isLoading = false, pokemon = pokemonList)
        }
    }

    private companion object {
        const val MAX_LOAD_ATTEMPTS = 5
        const val LOAD_RETRY_DELAY_MS = 250L
    }
}

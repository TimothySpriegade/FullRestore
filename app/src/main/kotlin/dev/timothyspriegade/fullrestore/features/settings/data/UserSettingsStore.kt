package dev.timothyspriegade.fullrestore.features.settings.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dev.timothyspriegade.fullrestore.core.common.USER_SETTINGS_DATASTORE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userSettingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_SETTINGS_DATASTORE
)

class UserSettingsStore(private val context: Context) {
    private val dataStore = context.userSettingsDataStore

    val isLightModeEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LIGHT_MODE_ENABLED] ?: false
    }

    val isTypeQuizEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_TYPE_QUIZ_ENABLED] ?: true
    }

    val isPokedexEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_POKEDEX_ENABLED] ?: true
    }

    val isTeamCoverageEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_TEAM_COVERAGE_ENABLED] ?: true
    }

    suspend fun setLightModeEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LIGHT_MODE_ENABLED] = enabled
        }
    }

    suspend fun setTypeQuizEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_TYPE_QUIZ_ENABLED] = enabled
        }
    }

    suspend fun setPokedexEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_POKEDEX_ENABLED] = enabled
        }
    }

    suspend fun setTeamCoverageEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_TEAM_COVERAGE_ENABLED] = enabled
        }
    }

    private companion object {
        val IS_LIGHT_MODE_ENABLED = booleanPreferencesKey("is_light_mode_enabled")
        val IS_TYPE_QUIZ_ENABLED = booleanPreferencesKey("is_type_quiz_enabled")
        val IS_POKEDEX_ENABLED = booleanPreferencesKey("is_pokedex_enabled")
        val IS_TEAM_COVERAGE_ENABLED = booleanPreferencesKey("is_team_coverage_enabled")
    }
}

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

    suspend fun setLightModeEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LIGHT_MODE_ENABLED] = enabled
        }
    }

    private companion object {
        val IS_LIGHT_MODE_ENABLED = booleanPreferencesKey("is_light_mode_enabled")
    }
}

package dev.timothyspriegade.fullrestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import dev.timothyspriegade.fullrestore.core.designsystem.AppNavItem
import dev.timothyspriegade.fullrestore.core.designsystem.FullRestoreTheme
import dev.timothyspriegade.fullrestore.features.settings.data.UserSettingsStore
import dev.timothyspriegade.fullrestore.features.settings.ui.SettingsScreen
import dev.timothyspriegade.fullrestore.features.typequiz.ui.TypeQuizRoute
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val settingsStore = remember { UserSettingsStore(context.applicationContext) }
            val isLightModeEnabled by settingsStore.isLightModeEnabled.collectAsState(initial = false)
            var selectedTab by rememberSaveable { mutableStateOf(AppTab.TYPE_QUIZ) }
            val scope = rememberCoroutineScope()

            FullRestoreTheme(darkTheme = !isLightModeEnabled) {
                val navItems = listOf(
                    AppNavItem(
                        label = "Type Quiz",
                        selected = selectedTab == AppTab.TYPE_QUIZ,
                        onClick = { selectedTab = AppTab.TYPE_QUIZ },
                        icon = {
                            Icon(
                                imageVector = Icons.Rounded.School,
                                contentDescription = "Type Quiz"
                            )
                        }
                    ),
                    AppNavItem(
                        label = "Settings",
                        selected = selectedTab == AppTab.SETTINGS,
                        onClick = { selectedTab = AppTab.SETTINGS },
                        icon = {
                            Icon(
                                imageVector = Icons.Rounded.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    )
                )

                Surface(modifier = Modifier.fillMaxSize()) {
                    when (selectedTab) {
                        AppTab.TYPE_QUIZ -> TypeQuizRoute(navItems = navItems)
                        AppTab.SETTINGS -> SettingsScreen(
                            isLightModeEnabled = isLightModeEnabled,
                            onLightModeToggle = { enabled ->
                                scope.launch { settingsStore.setLightModeEnabled(enabled) }
                            },
                            navItems = navItems
                        )
                    }
                }
            }
        }
    }
}

private enum class AppTab {
    TYPE_QUIZ,
    SETTINGS
}

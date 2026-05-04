package dev.timothyspriegade.fullrestore.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

private val NAVIGATION_BAR_HEIGHT = 64.dp
private val NAVIGATION_BAR_TONAL_ELEVATION = 0.dp
private val DEFAULT_ICON_SIZE = 10.dp

data class AppNavItem(
    val label: String,
    val selected: Boolean,
    val onClick: () -> Unit,
    val icon: @Composable (() -> Unit)? = null
)

@Composable
fun AppBottomNav(items: List<AppNavItem>, modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(NAVIGATION_BAR_HEIGHT),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = NAVIGATION_BAR_TONAL_ELEVATION,
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.selected,
                onClick = item.onClick,
                icon = {
                    item.icon?.invoke() ?: Box(
                        modifier = Modifier
                            .size(DEFAULT_ICON_SIZE)
                            .clip(CircleShape)
                            .background(VgcTeal)
                    )
                },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

package dev.timothyspriegade.fullrestore.features.settings.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ViewList
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.timothyspriegade.fullrestore.core.designsystem.AppBottomNav
import dev.timothyspriegade.fullrestore.core.designsystem.AppNavItem
import dev.timothyspriegade.fullrestore.core.designsystem.FullRestoreTheme
import dev.timothyspriegade.fullrestore.core.designsystem.SCREEN_PADDING_HORIZONTAL
import dev.timothyspriegade.fullrestore.core.designsystem.SCREEN_PADDING_VERTICAL
import dev.timothyspriegade.fullrestore.core.designsystem.VgcAmber
import dev.timothyspriegade.fullrestore.core.designsystem.VgcCoral
import dev.timothyspriegade.fullrestore.core.designsystem.VgcLeaf
import dev.timothyspriegade.fullrestore.core.designsystem.VgcTeal

private val SECTION_SPACING = 12.dp
private val CARD_CORNER_RADIUS = 24.dp
private val CARD_PADDING = 16.dp
private val ROW_SPACING = 12.dp
private val BADGE_SIZE = 40.dp
private val BADGE_ICON_SIZE = 20.dp
private val SECTION_HEADER_SPACING = 4.dp
private const val BADGE_ALPHA = 0.18f
private val FOOTER_SPACER_HEIGHT = 4.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isLightModeEnabled: Boolean,
    onLightModeToggle: (Boolean) -> Unit,
    isTypeQuizEnabled: Boolean,
    onTypeQuizToggle: (Boolean) -> Unit,
    isDefenseQuizEnabled: Boolean,
    onDefenseQuizToggle: (Boolean) -> Unit,
    isPokedexEnabled: Boolean,
    onPokedexToggle: (Boolean) -> Unit,
    isTeamCoverageEnabled: Boolean,
    onTeamCoverageToggle: (Boolean) -> Unit,
    navItems: List<AppNavItem>
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.background
        )
    )
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                title = {
                    Column {
                        Text("Settings", style = MaterialTheme.typography.titleLarge)
                        Text(
                            "Tune your experience",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        },
        bottomBar = {
            AppBottomNav(items = navItems)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = SCREEN_PADDING_HORIZONTAL,
                        vertical = SCREEN_PADDING_VERTICAL
                    )
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(SECTION_SPACING)
            ) {
                SectionHeader(
                    title = "Appearance",
                    subtitle = "Adjust the look of the app"
                )
                SettingsToggleCard(
                    title = "Light mode",
                    description = if (isLightModeEnabled) "Light palette active" else "Dark palette active",
                    enabled = isLightModeEnabled,
                    icon = Icons.Rounded.DarkMode,
                    badgeColor = if (isLightModeEnabled) VgcAmber else VgcLeaf,
                    iconTint = if (isLightModeEnabled) VgcAmber else VgcLeaf,
                    onToggle = onLightModeToggle
                )
                SectionHeader(
                    title = "Features",
                    subtitle = "Show or hide areas of the app"
                )
                SettingsToggleCard(
                    title = "Type Quiz",
                    description = if (isTypeQuizEnabled) "Visible in navigation" else "Hidden from navigation",
                    enabled = isTypeQuizEnabled,
                    icon = Icons.Rounded.School,
                    badgeColor = VgcTeal,
                    iconTint = VgcTeal,
                    onToggle = onTypeQuizToggle
                )
                SettingsToggleCard(
                    title = "Defense Quiz",
                    description = if (isDefenseQuizEnabled) "Visible in navigation" else "Hidden from navigation",
                    enabled = isDefenseQuizEnabled,
                    icon = Icons.Rounded.Shield,
                    badgeColor = VgcCoral,
                    iconTint = VgcCoral,
                    onToggle = onDefenseQuizToggle
                )
                SettingsToggleCard(
                    title = "Pokedex",
                    description = if (isPokedexEnabled) "Visible in navigation" else "Hidden from navigation",
                    enabled = isPokedexEnabled,
                    icon = Icons.AutoMirrored.Rounded.ViewList,
                    badgeColor = VgcLeaf,
                    iconTint = VgcLeaf,
                    onToggle = onPokedexToggle
                )
                SettingsToggleCard(
                    title = "Team coverage",
                    description = if (isTeamCoverageEnabled) "Visible in navigation" else "Hidden from navigation",
                    enabled = isTeamCoverageEnabled,
                    icon = Icons.Rounded.Analytics,
                    badgeColor = VgcAmber,
                    iconTint = VgcAmber,
                    onToggle = onTeamCoverageToggle
                )
                Spacer(modifier = Modifier.height(FOOTER_SPACER_HEIGHT))
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(SECTION_HEADER_SPACING)) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SettingsToggleCard(
    title: String,
    description: String,
    enabled: Boolean,
    icon: ImageVector,
    badgeColor: Color,
    iconTint: Color,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CARD_PADDING)
                .animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(ROW_SPACING)
        ) {
            Box(
                modifier = Modifier
                    .size(BADGE_SIZE)
                    .clip(CircleShape)
                    .background(badgeColor.copy(alpha = BADGE_ALPHA)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(BADGE_ICON_SIZE)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(
                checked = enabled,
                onCheckedChange = onToggle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    FullRestoreTheme(darkTheme = false) {
        SettingsScreen(
            isLightModeEnabled = false,
            onLightModeToggle = {},
            isTypeQuizEnabled = true,
            onTypeQuizToggle = {},
            isDefenseQuizEnabled = true,
            onDefenseQuizToggle = {},
            isPokedexEnabled = true,
            onPokedexToggle = {},
            isTeamCoverageEnabled = true,
            onTeamCoverageToggle = {},
            navItems = emptyList()
        )
    }
}



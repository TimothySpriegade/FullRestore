@file:OptIn(ExperimentalMaterial3Api::class)

package dev.timothyspriegade.fullrestore.features.teamcoverage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Casino
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.timothyspriegade.fullrestore.core.designsystem.AppBottomNav
import dev.timothyspriegade.fullrestore.core.designsystem.AppNavItem
import dev.timothyspriegade.fullrestore.core.designsystem.TypeBadge
import dev.timothyspriegade.fullrestore.core.designsystem.VgcCoral
import dev.timothyspriegade.fullrestore.core.designsystem.VgcLeaf
import dev.timothyspriegade.fullrestore.core.designsystem.VgcTeal
import dev.timothyspriegade.fullrestore.domain.model.Effectiveness
import dev.timothyspriegade.fullrestore.domain.model.PokemonType
import dev.timothyspriegade.fullrestore.domain.model.TeamCoverageResult
import dev.timothyspriegade.fullrestore.domain.model.TeamTypeSlot
import dev.timothyspriegade.fullrestore.features.teamcoverage.viewmodel.TeamCoverageViewModel
import kotlin.random.Random

private val SCREEN_PADDING_HORIZONTAL = 16.dp
private val SCREEN_PADDING_VERTICAL = 0.dp
private val SECTION_SPACING = 12.dp
private val SLOT_SPACING = 12.dp
private val SLOT_CARD_PADDING = 12.dp
private val SLOT_CARD_CORNER_RADIUS = 20.dp
private val SLOT_INDEX_SIZE = 28.dp
private val SLOT_INDEX_FONT_SIZE = 12.sp
private val TYPE_MENU_WIDTH = 200.dp
private val TYPE_DROPDOWN_SPACING = 8.dp
private val RESULT_ROW_SPACING = 12.dp
private val RESULT_CHIP_SPACING = 8.dp
private val RESULT_PANEL_SPACING = 10.dp
private val BOTTOM_SPACER_HEIGHT = 6.dp
private val LABEL_SPACING = 6.dp
private val RESULT_LABEL_SPACING = 8.dp
private val PILL_CORNER_RADIUS = 999.dp
private val TYPE_CHIP_HORIZONTAL_PADDING = 12.dp
private val TYPE_CHIP_VERTICAL_PADDING = 6.dp
private val EMPTY_STATE_SPACING = 10.dp
private val HERO_CORNER_RADIUS = 30.dp
private val HERO_PADDING = 20.dp
private val HERO_BADGE_CORNER_RADIUS = 14.dp
private val HERO_BADGE_HORIZONTAL_PADDING = 10.dp
private val HERO_BADGE_VERTICAL_PADDING = 4.dp
private val HERO_BADGE_SPACING = 10.dp
private val HERO_STAT_DOT_SIZE = 6.dp
private val HERO_STAT_SPACING = 6.dp
private val HERO_STAT_ROW_SPACING = 12.dp
private val ACTIONS_SPACING = 8.dp
private val ACTION_ICON_SIZE = 18.dp
private val SECTION_HEADER_SPACING = 4.dp
private val RESULT_PILL_HORIZONTAL_PADDING = 12.dp
private val RESULT_PILL_VERTICAL_PADDING = 6.dp
private val GROUP_CARD_CORNER_RADIUS = 24.dp
private val GROUP_CARD_PADDING = 16.dp
private val GROUP_CARD_SPACING = 12.dp
private val GROUP_HEADER_SPACING = 6.dp
private val EMPTY_ICON_SIZE = 18.dp
private val EMPTY_ICON_CONTAINER = 32.dp
private val EMPTY_ICON_CORNER_RADIUS = 10.dp
private val MENU_ROW_SPACING = 10.dp
private val MENU_CHECK_SIZE = 18.dp
private val SLOT_PILL_HEIGHT = 36.dp
private val MENU_DIVIDER_PADDING = 6.dp
private val SLOT_CARD_ELEVATION = 2.dp
private val EMPTY_ICON_ALPHA = 0.12f
private val MENU_ITEM_PADDING = 8.dp

@Composable
fun TeamCoverageRoute(
    navItems: List<AppNavItem>,
    viewModel: TeamCoverageViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    TeamCoverageScreen(
        navItems = navItems,
        slots = state.slots,
        result = state.result,
        onSlotChange = viewModel::updateSlot,
        onClear = viewModel::clearTeam,
        onRandomFill = {
            val types = PokemonType.entries
            repeat(6) { index ->
                val primary = types.random()
                val secondary = if (Random.nextBoolean()) {
                    types.filter { it != primary }.random()
                } else {
                    null
                }
                viewModel.updateSlot(index, primary, secondary)
            }
        }
    )
}

@Composable
private fun TeamCoverageScreen(
    navItems: List<AppNavItem>,
    slots: List<TeamTypeSlot>,
    result: TeamCoverageResult?,
    onSlotChange: (Int, PokemonType?, PokemonType?) -> Unit,
    onClear: () -> Unit,
    onRandomFill: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.background
        )
    )
    val heroGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.surface
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
                    Column(verticalArrangement = Arrangement.spacedBy(SECTION_HEADER_SPACING)) {
                        Text("Team Coverage", style = MaterialTheme.typography.titleLarge)
                        Text(
                            "Six-slot type analysis for VGC teams",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        },
        bottomBar = { AppBottomNav(items = navItems) }
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
                HeroCard(
                    heroGradient = heroGradient,
                    onRandomFill = onRandomFill,
                    onClear = onClear,
                    hasSelections = slots.any { it.primary != null }
                )
                TeamInputCard(
                    slots = slots,
                    onSlotChange = onSlotChange
                )
                if (result != null) {
                    CoveragePanel(
                        title = "Offensive coverage",
                        subtitle = "Best possible hit from your team",
                        result = result.offensive
                    )
                    CoveragePanel(
                        title = "Defensive profile",
                        subtitle = "Worst weakness across your team",
                        result = result.defensive
                    )
                    WeaknessSummaryPanel(
                        weaknessCounts = result.weaknessCounts
                    )
                } else {
                    EmptyStateCard()
                }
                Spacer(modifier = Modifier.height(BOTTOM_SPACER_HEIGHT))
            }
        }
    }
}

@Composable
private fun HeroCard(
    heroGradient: Brush,
    onRandomFill: () -> Unit,
    onClear: () -> Unit,
    hasSelections: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(HERO_CORNER_RADIUS),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .background(heroGradient)
                .padding(HERO_PADDING),
            verticalArrangement = Arrangement.spacedBy(HERO_BADGE_SPACING)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
                shape = RoundedCornerShape(HERO_BADGE_CORNER_RADIUS)
            ) {
                Text(
                    "TEAM INSIGHT",
                    modifier = Modifier.padding(
                        horizontal = HERO_BADGE_HORIZONTAL_PADDING,
                        vertical = HERO_BADGE_VERTICAL_PADDING
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Text(
                "Build a six-slot type core",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Column(verticalArrangement = Arrangement.spacedBy(HERO_STAT_ROW_SPACING)) {
                HeroStatRow(
                    title = "Offense",
                    description = "Best damage your types can apply"
                )
                HeroStatRow(
                    title = "Defense",
                    description = "Worst-case weaknesses across the team"
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(ACTIONS_SPACING)) {
                FilledTonalButton(
                    onClick = onRandomFill,
                    contentPadding = PaddingValues(
                        horizontal = TYPE_CHIP_HORIZONTAL_PADDING,
                        vertical = TYPE_CHIP_VERTICAL_PADDING
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Casino,
                        contentDescription = null,
                        modifier = Modifier.size(ACTION_ICON_SIZE)
                    )
                    Spacer(modifier = Modifier.width(TYPE_DROPDOWN_SPACING))
                    Text("Randomize")
                }
                OutlinedButton(
                    onClick = onClear,
                    enabled = hasSelections,
                    contentPadding = PaddingValues(
                        horizontal = TYPE_CHIP_HORIZONTAL_PADDING,
                        vertical = TYPE_CHIP_VERTICAL_PADDING
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = null,
                        modifier = Modifier.size(ACTION_ICON_SIZE)
                    )
                    Spacer(modifier = Modifier.width(TYPE_DROPDOWN_SPACING))
                    Text("Clear all")
                }
            }
        }
    }
}

@Composable
private fun HeroStatRow(title: String, description: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(HERO_STAT_DOT_SIZE)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(HERO_STAT_SPACING))
        Column {
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
    }
}

@Composable
private fun TeamInputCard(
    slots: List<TeamTypeSlot>,
    onSlotChange: (Int, PokemonType?, PokemonType?) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(GROUP_CARD_CORNER_RADIUS),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(GROUP_CARD_PADDING),
            verticalArrangement = Arrangement.spacedBy(GROUP_CARD_SPACING)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(GROUP_HEADER_SPACING)) {
                Text(
                    "Team slots",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    "Primary type required, secondary optional",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            slots.forEachIndexed { index, slot ->
                TeamSlotRow(
                    slotIndex = index + 1,
                    slot = slot,
                    onSlotChange = { primary, secondary ->
                        onSlotChange(index, primary, secondary)
                    }
                )
            }
        }
    }
}

@Composable
private fun TeamSlotRow(
    slotIndex: Int,
    slot: TeamTypeSlot,
    onSlotChange: (PokemonType?, PokemonType?) -> Unit
) {
    Card(
        shape = RoundedCornerShape(SLOT_CARD_CORNER_RADIUS),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(SLOT_CARD_ELEVATION)
        )
    ) {
        Column(
            modifier = Modifier.padding(SLOT_CARD_PADDING),
            verticalArrangement = Arrangement.spacedBy(SLOT_SPACING)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(
                        modifier = Modifier.size(SLOT_INDEX_SIZE),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            slotIndex.toString(),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = SLOT_INDEX_FONT_SIZE
                        )
                    }
                }
                Spacer(modifier = Modifier.width(TYPE_DROPDOWN_SPACING))
                Text(
                    "Slot $slotIndex",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(TYPE_DROPDOWN_SPACING)) {
                TypeSelector(
                    label = "Primary",
                    selected = slot.primary,
                    modifier = Modifier.weight(1f),
                    onSelect = { onSelect -> onSlotChange(onSelect, slot.secondary) }
                )
                TypeSelector(
                    label = "Secondary",
                    selected = slot.secondary,
                    allowClear = true,
                    modifier = Modifier.weight(1f),
                    onSelect = { onSelect -> onSlotChange(slot.primary, onSelect) }
                )
            }
            if (slot.primary != null) {
                SlotWeaknessAnalysis(slot = slot)
            }
        }
    }
}

@Composable
private fun SlotWeaknessAnalysis(slot: TeamTypeSlot) {
    val defensiveProfile = remember(slot) {
        val primary = slot.primary ?: return@remember emptyMap<Effectiveness, List<PokemonType>>()
        val secondary = slot.secondary
        PokemonType.entries.groupBy { attacker ->
            val primaryMult = dev.timothyspriegade.fullrestore.core.util.TypeChart.effectiveness(
                attacker,
                primary
            )
            val secondaryMult = secondary?.let {
                dev.timothyspriegade.fullrestore.core.util.TypeChart.effectiveness(
                    attacker,
                    it
                )
            } ?: 1.0
            Effectiveness.fromMultiplier(primaryMult * secondaryMult)
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(LABEL_SPACING)) {
        HorizontalDivider(
            modifier = Modifier.padding(vertical = LABEL_SPACING),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        Text(
            "Defensive Analysis",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Effectiveness.ordered.filter { it != Effectiveness.NEUTRAL }.forEach { bucket ->
            val types = defensiveProfile[bucket].orEmpty()
            if (types.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(LABEL_SPACING)) {
                    Text(
                        bucket.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = when (bucket) {
                            Effectiveness.FOUR_X, Effectiveness.TWO_X -> VgcCoral
                            else -> VgcLeaf
                        }
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(RESULT_CHIP_SPACING),
                        verticalArrangement = Arrangement.spacedBy(RESULT_CHIP_SPACING)
                    ) {
                        types.forEach { type ->
                            TypeBadge(type = type)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TypeSelector(
    label: String,
    selected: PokemonType?,
    modifier: Modifier = Modifier,
    allowClear: Boolean = false,
    onSelect: (PokemonType?) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(LABEL_SPACING)
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        TypeDropdown(
            selected = selected,
            allowClear = allowClear,
            onSelect = onSelect
        )
    }
}

@Composable
private fun TypeDropdown(
    selected: PokemonType?,
    allowClear: Boolean,
    onSelect: (PokemonType?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val label = selected?.displayName ?: "Select"
    Box {
        Column(verticalArrangement = Arrangement.spacedBy(TYPE_DROPDOWN_SPACING)) {
            FilledTonalButton(
                onClick = { expanded = true },
                contentPadding = PaddingValues(
                    horizontal = TYPE_CHIP_HORIZONTAL_PADDING,
                    vertical = TYPE_CHIP_VERTICAL_PADDING
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SLOT_PILL_HEIGHT)
            ) {
                Text(
                    text = label,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(TYPE_DROPDOWN_SPACING))
                Icon(
                    imageVector = Icons.Rounded.ExpandMore,
                    contentDescription = null,
                    modifier = Modifier.size(ACTION_ICON_SIZE)
                )
            }
            if (allowClear && selected != null) {
                TextButton(
                    onClick = { onSelect(null) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        modifier = Modifier.size(ACTION_ICON_SIZE)
                    )
                    Spacer(modifier = Modifier.width(TYPE_DROPDOWN_SPACING))
                    Text("Clear")
                }
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownSectionHeader(title = "Primary types")
            PokemonType.entries.forEach { type ->
                TypeMenuItem(
                    type = type,
                    isSelected = type == selected,
                    onClick = {
                        onSelect(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun DropdownSectionHeader(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = TYPE_CHIP_HORIZONTAL_PADDING, vertical = MENU_DIVIDER_PADDING),
        verticalArrangement = Arrangement.spacedBy(MENU_DIVIDER_PADDING)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Composable
private fun TypeMenuItem(
    type: PokemonType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.width(TYPE_MENU_WIDTH)
            ) {
                TypeBadge(type = type)
                Spacer(modifier = Modifier.width(MENU_ROW_SPACING))
                Text(
                    type.displayName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(MENU_CHECK_SIZE)
                    )
                }
            }
        },
        onClick = onClick,
        modifier = Modifier.padding(horizontal = MENU_ITEM_PADDING)
    )
}

@Composable
private fun WeaknessSummaryPanel(
    weaknessCounts: Map<PokemonType, Int>
) {
    if (weaknessCounts.isEmpty()) return

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(GROUP_CARD_CORNER_RADIUS),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(GROUP_CARD_PADDING),
            verticalArrangement = Arrangement.spacedBy(RESULT_PANEL_SPACING)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(GROUP_HEADER_SPACING)) {
                Text(
                    "Weakness stacking",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    "Count of team members weak to each type",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Group by count of weaknesses
            val counts = weaknessCounts.values.distinct().sortedDescending()
            counts.forEach { count ->
                val types =
                    weaknessCounts.filter { it.value == count }.keys.sortedBy { it.displayName }
                Column(verticalArrangement = Arrangement.spacedBy(RESULT_ROW_SPACING)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(PILL_CORNER_RADIUS),
                            color = VgcCoral.copy(alpha = 0.12f)
                        ) {
                            Text(
                                "$count weak",
                                modifier = Modifier.padding(
                                    horizontal = RESULT_PILL_HORIZONTAL_PADDING,
                                    vertical = RESULT_PILL_VERTICAL_PADDING
                                ),
                                style = MaterialTheme.typography.labelLarge,
                                color = VgcCoral,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    FlowRowChips(types = types)
                }
            }
        }
    }
}

@Composable
private fun CoveragePanel(
    title: String,
    subtitle: String,
    result: Map<Effectiveness, List<PokemonType>>
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(GROUP_CARD_CORNER_RADIUS),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(GROUP_CARD_PADDING),
            verticalArrangement = Arrangement.spacedBy(RESULT_PANEL_SPACING)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(GROUP_HEADER_SPACING)) {
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
            Effectiveness.ordered.forEach { bucket ->
                val types = result[bucket].orEmpty()
                CoverageRow(bucket = bucket, types = types)
            }
        }
    }
}

@Composable
private fun CoverageRow(bucket: Effectiveness, types: List<PokemonType>) {
    val accent = when (bucket) {
        Effectiveness.FOUR_X, Effectiveness.TWO_X -> VgcLeaf
        Effectiveness.NEUTRAL -> VgcTeal
        Effectiveness.HALF_X, Effectiveness.QUARTER_X, Effectiveness.IMMUNE -> VgcCoral
    }
    Column(verticalArrangement = Arrangement.spacedBy(RESULT_ROW_SPACING)) {
        CoverageHeader(bucket = bucket, count = types.size, accent = accent)
        if (types.isNotEmpty()) {
            FlowRowChips(types = types)
        } else {
            EmptyCoverageChips()
        }
    }
}

@Composable
private fun CoverageHeader(bucket: Effectiveness, count: Int, accent: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            shape = RoundedCornerShape(PILL_CORNER_RADIUS),
            color = accent.copy(alpha = 0.12f)
        ) {
            Text(
                bucket.label,
                modifier = Modifier.padding(
                    horizontal = RESULT_PILL_HORIZONTAL_PADDING,
                    vertical = RESULT_PILL_VERTICAL_PADDING
                ),
                style = MaterialTheme.typography.labelLarge,
                color = accent,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.width(RESULT_LABEL_SPACING))
        Text(
            "$count types",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun EmptyCoverageChips() {
    Text(
        "None",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(vertical = LABEL_SPACING)
    )
}

@Composable
private fun FlowRowChips(types: List<PokemonType>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(RESULT_CHIP_SPACING),
        verticalArrangement = Arrangement.spacedBy(RESULT_CHIP_SPACING),
        modifier = Modifier.fillMaxWidth()
    ) {
        types.forEach { type ->
            TypeBadge(type = type)
        }
    }
}

@Composable
private fun EmptyStateCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(GROUP_CARD_CORNER_RADIUS),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(GROUP_CARD_PADDING),
            verticalArrangement = Arrangement.spacedBy(EMPTY_STATE_SPACING)
        ) {
            Surface(
                shape = RoundedCornerShape(EMPTY_ICON_CORNER_RADIUS),
                color = MaterialTheme.colorScheme.primary.copy(alpha = EMPTY_ICON_ALPHA)
            ) {
                Box(
                    modifier = Modifier.size(EMPTY_ICON_CONTAINER),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(EMPTY_ICON_SIZE)
                    )
                }
            }
            Text(
                "Select at least one primary type",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                "Once you do, offensive and defensive coverage will appear here.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun TeamCoverageScreenPreview() {
    MaterialTheme {
        TeamCoverageScreen(
            navItems = emptyList(),
            slots = listOf(
                TeamTypeSlot(PokemonType.FIRE, PokemonType.FLYING),
                TeamTypeSlot(PokemonType.WATER, null),
                TeamTypeSlot(null, null),
                TeamTypeSlot(null, null),
                TeamTypeSlot(null, null),
                TeamTypeSlot(null, null)
            ),
            result = TeamCoverageResult(
                offensive = mapOf(
                    Effectiveness.TWO_X to listOf(
                        PokemonType.GRASS,
                        PokemonType.ICE,
                        PokemonType.BUG,
                        PokemonType.STEEL
                    )
                ),
                defensive = mapOf(
                    Effectiveness.FOUR_X to listOf(PokemonType.ROCK),
                    Effectiveness.TWO_X to listOf(PokemonType.WATER, PokemonType.ELECTRIC)
                ),
                weaknessCounts = mapOf(
                    PokemonType.ROCK to 1,
                    PokemonType.WATER to 1,
                    PokemonType.ELECTRIC to 1
                )
            ),
            onSlotChange = { _, _, _ -> },
            onClear = {},
            onRandomFill = {}
        )
    }
}

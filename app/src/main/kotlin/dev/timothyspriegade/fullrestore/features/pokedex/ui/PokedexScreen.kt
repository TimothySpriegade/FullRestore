@file:OptIn(ExperimentalMaterial3Api::class)

package dev.timothyspriegade.fullrestore.features.pokedex.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import dev.timothyspriegade.fullrestore.core.designsystem.AppBottomNav
import dev.timothyspriegade.fullrestore.core.designsystem.AppNavItem
import dev.timothyspriegade.fullrestore.core.designsystem.TypeBadge
import dev.timothyspriegade.fullrestore.core.designsystem.VgcLeaf
import dev.timothyspriegade.fullrestore.data.local.pokemon.PokemonEntity
import dev.timothyspriegade.fullrestore.domain.model.PokemonType
import dev.timothyspriegade.fullrestore.features.pokedex.viewmodel.PokedexViewModel

private val SCREEN_PADDING_HORIZONTAL = 16.dp
private val SCREEN_PADDING_VERTICAL = 0.dp
private val SECTION_SPACING = 12.dp
private val CARD_CORNER_RADIUS = 24.dp
private val CARD_PADDING = 16.dp
private val CARD_SPACING = 12.dp
private val TYPE_SPACING = 8.dp
private val STATS_ROW_SPACING = 10.dp
private val STAT_BADGE_SIZE = 10.dp
private val FILTER_PANEL_CORNER_RADIUS = 20.dp
private val FILTER_PANEL_PADDING = 14.dp
private val FILTER_SPACING = 10.dp
private val FILTER_CHIP_SPACING = 8.dp
private val SPRITE_SIZE = 68.dp

@Composable
fun PokedexRoute(
    navItems: List<AppNavItem>,
    viewModel: PokedexViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState()
    PokedexScreen(
        navItems = navItems,
        pokemon = state.value.pokemon,
        isLoading = state.value.isLoading
    )
}

@Composable
fun PokedexScreen(
    navItems: List<AppNavItem>,
    pokemon: List<PokemonEntity>,
    isLoading: Boolean
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.background
        )
    )
    val queryState = remember { mutableStateOf(TextFieldValue("")) }
    val query = queryState.value.text.trim()
    var isFilterOpen by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf<PokemonType?>(null) }
    var showLegendaryOnly by remember { mutableStateOf(false) }
    var showMythicalOnly by remember { mutableStateOf(false) }
    var showBabyOnly by remember { mutableStateOf(false) }
    var minBaseStatTotal by remember { mutableIntStateOf(0) }
    val filteredPokemon = pokemon.filter { entry ->
        if (query.isBlank()) return@filter true
        val normalized = query.lowercase()
        entry.name.lowercase().contains(normalized) ||
                entry.pokedexNumber.toString().contains(normalized) ||
                entry.type1.lowercase().contains(normalized) ||
                entry.type2?.lowercase()?.contains(normalized) == true
    }
        .filter { entry ->
            selectedType?.let {
                entry.type1.equals(it.displayName, ignoreCase = true) ||
                        entry.type2?.equals(it.displayName, ignoreCase = true) == true
            } ?: true
        }
        .filter { entry ->
            (!showLegendaryOnly || entry.isLegendary) &&
                    (!showMythicalOnly || entry.isMythical) &&
                    (!showBabyOnly || entry.isBaby)
        }
        .filter { entry ->
            entry.baseStatTotal >= minBaseStatTotal
        }

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
                        Text("Pokedex", style = MaterialTheme.typography.titleLarge)
                        Text(
                            "Browse every Pokemon",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { isFilterOpen = !isFilterOpen }) {
                        Icon(
                            imageVector = Icons.Rounded.FilterList,
                            contentDescription = "Filters"
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
                    ),
                verticalArrangement = Arrangement.spacedBy(SECTION_SPACING)
            ) {
                OutlinedTextField(
                    value = queryState.value,
                    onValueChange = { queryState.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Search by name, number, or type") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
                    },
                    singleLine = true
                )
                AnimatedVisibility(visible = isFilterOpen) {
                    FilterPanel(
                        selectedType = selectedType,
                        onTypeSelected = { selectedType = it },
                        showLegendaryOnly = showLegendaryOnly,
                        onLegendaryToggle = { showLegendaryOnly = it },
                        showMythicalOnly = showMythicalOnly,
                        onMythicalToggle = { showMythicalOnly = it },
                        showBabyOnly = showBabyOnly,
                        onBabyToggle = { showBabyOnly = it },
                        minBaseStatTotal = minBaseStatTotal,
                        onMinBaseStatTotalChange = { minBaseStatTotal = it }
                    )
                }
                AnimatedVisibility(visible = isLoading) {
                    Text(
                        "Loading pokedex...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (!isLoading) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(CARD_SPACING)
                    ) {
                        items(filteredPokemon, key = { it.pokedexNumber }) { entry ->
                            PokedexCard(entry = entry)
                        }
                        item {
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PokedexCard(entry: PokemonEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(CARD_PADDING),
            verticalArrangement = Arrangement.spacedBy(SECTION_SPACING)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "#${entry.pokedexNumber}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        entry.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                AsyncImage(
                    model = entry.spriteUrl,
                    contentDescription = "${entry.name} sprite",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(SPRITE_SIZE)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(TYPE_SPACING)) {
                    parseType(entry.type1)?.let { type ->
                        TypeBadge(type = type)
                    }
                    entry.type2?.let { secondary ->
                        parseType(secondary)?.let { type ->
                            TypeBadge(type = type)
                        }
                    }
                }
                if (entry.baseStatTotal > 0) {
                    StatPill(label = "BST", value = entry.baseStatTotal)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(STATS_ROW_SPACING)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatRow(label = "HP", value = entry.hp, modifier = Modifier.weight(1f))
                    StatRow(label = "Atk", value = entry.attack, modifier = Modifier.weight(1f))
                    StatRow(label = "Def", value = entry.defense, modifier = Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatRow(label = "SpA", value = entry.spAttack, modifier = Modifier.weight(1f))
                    StatRow(label = "SpD", value = entry.spDefense, modifier = Modifier.weight(1f))
                    StatRow(label = "Spe", value = entry.speed, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(STAT_BADGE_SIZE)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            value.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun StatPill(label: String, value: Int) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            value.toString(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun FilterPanel(
    selectedType: PokemonType?,
    onTypeSelected: (PokemonType?) -> Unit,
    showLegendaryOnly: Boolean,
    onLegendaryToggle: (Boolean) -> Unit,
    showMythicalOnly: Boolean,
    onMythicalToggle: (Boolean) -> Unit,
    showBabyOnly: Boolean,
    onBabyToggle: (Boolean) -> Unit,
    minBaseStatTotal: Int,
    onMinBaseStatTotalChange: (Int) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(FILTER_PANEL_CORNER_RADIUS),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(FILTER_PANEL_PADDING),
            verticalArrangement = Arrangement.spacedBy(FILTER_SPACING)
        ) {
            Text(
                "Filters",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                "Type",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TypeFilterRow(
                selectedType = selectedType,
                onTypeSelected = onTypeSelected
            )
            Text(
                "Tags",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(horizontalArrangement = Arrangement.spacedBy(FILTER_CHIP_SPACING)) {
                FilterChip(
                    label = "Legendary",
                    selected = showLegendaryOnly,
                    onClick = { onLegendaryToggle(!showLegendaryOnly) }
                )
                FilterChip(
                    label = "Mythical",
                    selected = showMythicalOnly,
                    onClick = { onMythicalToggle(!showMythicalOnly) }
                )
                FilterChip(
                    label = "Baby",
                    selected = showBabyOnly,
                    onClick = { onBabyToggle(!showBabyOnly) }
                )
            }
            Text(
                "Minimum base stat total: $minBaseStatTotal",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Slider(
                value = minBaseStatTotal.toFloat(),
                onValueChange = { onMinBaseStatTotalChange(it.toInt()) },
                valueRange = 0f..700f,
                steps = 13
            )
        }
    }
}

@Composable
private fun TypeFilterRow(
    selectedType: PokemonType?,
    onTypeSelected: (PokemonType?) -> Unit
) {
    val types = PokemonType.entries
    Column(verticalArrangement = Arrangement.spacedBy(FILTER_CHIP_SPACING)) {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(FILTER_CHIP_SPACING)
        ) {
            FilterChip(
                label = "All",
                selected = selectedType == null,
                onClick = { onTypeSelected(null) }
            )
            FilterChip(
                label = types[0].displayName,
                selected = selectedType == types[0],
                onClick = { onTypeSelected(types[0]) }
            )
            FilterChip(
                label = types[1].displayName,
                selected = selectedType == types[1],
                onClick = { onTypeSelected(types[1]) }
            )
            FilterChip(
                label = types[2].displayName,
                selected = selectedType == types[2],
                onClick = { onTypeSelected(types[2]) }
            )
        }
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(FILTER_CHIP_SPACING)
        ) {
            types.drop(3).take(4).forEach { type ->
                FilterChip(
                    label = type.displayName,
                    selected = selectedType == type,
                    onClick = { onTypeSelected(type) }
                )
            }
        }
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(FILTER_CHIP_SPACING)
        ) {
            types.drop(7).take(4).forEach { type ->
                FilterChip(
                    label = type.displayName,
                    selected = selectedType == type,
                    onClick = { onTypeSelected(type) }
                )
            }
        }
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(FILTER_CHIP_SPACING)
        ) {
            types.drop(11).take(4).forEach { type ->
                FilterChip(
                    label = type.displayName,
                    selected = selectedType == type,
                    onClick = { onTypeSelected(type) }
                )
            }
        }
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(FILTER_CHIP_SPACING)
        ) {
            types.drop(15).take(3).forEach { type ->
                FilterChip(
                    label = type.displayName,
                    selected = selectedType == type,
                    onClick = { onTypeSelected(type) }
                )
            }
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
    }
    val contentColor = if (selected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = backgroundColor,
        contentColor = contentColor,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(VgcLeaf)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(label, style = MaterialTheme.typography.labelLarge)
        }
    }
}

private fun parseType(raw: String): PokemonType? {
    return PokemonType.entries.firstOrNull { it.displayName.equals(raw, ignoreCase = true) }
}

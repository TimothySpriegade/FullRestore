@file:OptIn(ExperimentalMaterial3Api::class)

package dev.timothyspriegade.fullrestore.features.defensequiz.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.timothyspriegade.fullrestore.core.common.APP_TAGLINE
import dev.timothyspriegade.fullrestore.core.designsystem.AppBottomNav
import dev.timothyspriegade.fullrestore.core.designsystem.AppNavItem
import dev.timothyspriegade.fullrestore.core.designsystem.TypeBadge
import dev.timothyspriegade.fullrestore.core.designsystem.VgcCoral
import dev.timothyspriegade.fullrestore.core.designsystem.VgcLeaf
import dev.timothyspriegade.fullrestore.core.designsystem.VgcTeal
import dev.timothyspriegade.fullrestore.domain.model.Effectiveness
import dev.timothyspriegade.fullrestore.domain.model.PokemonType
import dev.timothyspriegade.fullrestore.features.defensequiz.viewmodel.DefenseQuizViewModel

// Layout constants
private val SCREEN_PADDING_HORIZONTAL = 16.dp
private val MAIN_CONTENT_SPACING = 12.dp
private val TOP_BAR_ACTION_SPACING = 16.dp
private val BOTTOM_SPACER_HEIGHT = 24.dp

// Card constants
private val CARD_CORNER_RADIUS_LARGE = 24.dp
private val CARD_CORNER_RADIUS_MEDIUM = 16.dp
private val CARD_CONTENT_PADDING = 16.dp
private val BUCKET_CONTENT_PADDING = 12.dp
private val BUCKET_SPACING = 8.dp
private val SECTION_SPACING = 12.dp

// Streak Badge constants
private val STREAK_BADGE_CORNER_RADIUS = 999.dp
private const val STREAK_BADGE_ALPHA = 0.14f
private val STREAK_BADGE_HORIZONTAL_PADDING = 12.dp
private val STREAK_BADGE_VERTICAL_PADDING = 6.dp
private val STREAK_BADGE_DOT_SIZE = 8.dp
private val STREAK_BADGE_SPACING = 8.dp

// Badge & Icon constants
private val BADGE_WIDTH = 100.dp
private val STATUS_ICON_SIZE = 16.dp
private val STATUS_ICON_INNER_SIZE = 12.dp
private val STATUS_ICON_PADDING = 2.dp
private val BUTTON_CORNER_RADIUS = 16.dp
private val BUTTON_ICON_SPACING = 8.dp
private val SELECTION_BORDER_WIDTH = 2.dp

@Composable
fun DefenseQuizRoute(
    navItems: List<AppNavItem>,
    viewModel: DefenseQuizViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    DefenseQuizScreen(
        defenderPrimary = state.defenderPrimary,
        defenderSecondary = state.defenderSecondary,
        assignments = state.assignments,
        correctAssignments = state.correctAssignments,
        validationResults = state.validationResults,
        isSubmitted = state.isSubmitted,
        streak = state.streak,
        selectedType = state.selectedType,
        onTypeSelect = viewModel::selectType,
        onBucketClick = viewModel::assignSelectedToBucket,
        onAssign = viewModel::assignType,
        onFillRemaining = viewModel::assignAllUnassignedToNeutral,
        onSubmit = viewModel::submit,
        onNext = viewModel::nextQuestion,
        navItems = navItems
    )
}

@Composable
fun DefenseQuizScreen(
    defenderPrimary: PokemonType?,
    defenderSecondary: PokemonType?,
    assignments: Map<PokemonType, Effectiveness?>,
    correctAssignments: Map<PokemonType, Effectiveness>?,
    validationResults: Map<PokemonType, Boolean>?,
    isSubmitted: Boolean,
    streak: Int,
    selectedType: PokemonType?,
    onTypeSelect: (PokemonType?) -> Unit,
    onBucketClick: (Effectiveness) -> Unit,
    onAssign: (PokemonType, Effectiveness?) -> Unit,
    onFillRemaining: () -> Unit,
    onSubmit: () -> Unit,
    onNext: () -> Unit,
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
                        Text("Defense Quiz", style = MaterialTheme.typography.titleLarge)
                        Text(
                            APP_TAGLINE,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    StreakBadge(streak = streak)
                    Spacer(modifier = Modifier.width(TOP_BAR_ACTION_SPACING))
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
                    .padding(horizontal = SCREEN_PADDING_HORIZONTAL)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(MAIN_CONTENT_SPACING)
            ) {
                DefenderCard(primary = defenderPrimary, secondary = defenderSecondary)

                EffectivenessBuckets(
                    assignments = assignments,
                    correctAssignments = correctAssignments,
                    validationResults = validationResults,
                    selectedType = selectedType,
                    onTypeSelect = onTypeSelect,
                    onBucketClick = onBucketClick,
                    onAssign = onAssign,
                    isSubmitted = isSubmitted
                )

                UnassignedTypes(
                    assignments = assignments,
                    validationResults = validationResults,
                    selectedType = selectedType,
                    onTypeSelect = onTypeSelect,
                    onFillRemaining = onFillRemaining,
                    isSubmitted = isSubmitted
                )

                Spacer(modifier = Modifier.height(BUCKET_SPACING))

                if (!isSubmitted) {
                    Button(
                        onClick = onSubmit,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(BUTTON_CORNER_RADIUS)
                    ) {
                        Text("Check Answers")
                    }
                } else {
                    Button(
                        onClick = onNext,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(BUTTON_CORNER_RADIUS),
                        colors = ButtonDefaults.buttonColors(containerColor = VgcTeal)
                    ) {
                        Icon(Icons.Rounded.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(BUTTON_ICON_SPACING))
                        Text("Next Defender")
                    }
                }
                Spacer(modifier = Modifier.height(BOTTOM_SPACER_HEIGHT))
            }
        }
    }
}

@Composable
private fun DefenderCard(primary: PokemonType?, secondary: PokemonType?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS_LARGE),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(CARD_CONTENT_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Categorize all attacker types for:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(MAIN_CONTENT_SPACING))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(BUCKET_SPACING)
            ) {
                if (primary != null) TypeBadge(type = primary)
                if (secondary != null) TypeBadge(type = secondary)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EffectivenessBuckets(
    assignments: Map<PokemonType, Effectiveness?>,
    correctAssignments: Map<PokemonType, Effectiveness>?,
    validationResults: Map<PokemonType, Boolean>?,
    selectedType: PokemonType?,
    onTypeSelect: (PokemonType?) -> Unit,
    onBucketClick: (Effectiveness) -> Unit,
    onAssign: (PokemonType, Effectiveness?) -> Unit,
    isSubmitted: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(BUCKET_SPACING)) {
        Effectiveness.ordered.forEach { effectiveness ->
            val typesInBucket = assignments.filter { it.value == effectiveness }.keys.toList()
            val correctInBucket = correctAssignments?.filter { it.value == effectiveness }?.keys?.toList() ?: emptyList()
            
            // Missing types: Types that SHOULD be here but the user didn't place here
            val missingTypes = if (isSubmitted) {
                correctInBucket.filter { it !in typesInBucket }
            } else emptyList()

            val bucketColor by animateColorAsState(
                if (selectedType != null && !isSubmitted) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                else MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                label = "bucket_highlight"
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = { if (selectedType != null) onBucketClick(effectiveness) },
                enabled = selectedType != null && !isSubmitted,
                shape = RoundedCornerShape(BUTTON_CORNER_RADIUS),
                colors = CardDefaults.cardColors(containerColor = bucketColor)
            ) {
                Column(modifier = Modifier.padding(BUCKET_CONTENT_PADDING)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = effectiveness.label,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedType != null && !isSubmitted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                        if (selectedType != null && !isSubmitted) {
                            Text(
                                "Tap to assign",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(BUCKET_SPACING))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(BUCKET_SPACING),
                        verticalArrangement = Arrangement.spacedBy(BUCKET_SPACING),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (typesInBucket.isEmpty() && missingTypes.isEmpty()) {
                            Text(
                                "None",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                        }
                        // User's placements
                        typesInBucket.forEach { type ->
                            SelectableTypeBadge(
                                type = type,
                                isSelected = selectedType == type,
                                isCorrect = validationResults?.get(type),
                                onClick = {
                                    if (!isSubmitted) {
                                        if (selectedType == type) onTypeSelect(null)
                                        else onTypeSelect(type)
                                    }
                                }
                            )
                        }
                        // Missing correct answers
                        missingTypes.forEach { type ->
                            Box(modifier = Modifier.width(BADGE_WIDTH).alpha(0.5f)) {
                                TypeBadge(
                                    type = type,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(STATUS_ICON_SIZE)
                                        .background(VgcTeal, CircleShape)
                                        .padding(STATUS_ICON_PADDING),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = "Should be here",
                                        tint = Color.White,
                                        modifier = Modifier.size(STATUS_ICON_INNER_SIZE)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun UnassignedTypes(
    assignments: Map<PokemonType, Effectiveness?>,
    validationResults: Map<PokemonType, Boolean>?,
    selectedType: PokemonType?,
    onTypeSelect: (PokemonType?) -> Unit,
    onFillRemaining: () -> Unit,
    isSubmitted: Boolean
) {
    val unassigned = assignments.filter { it.value == null }.keys.toList()
    if (unassigned.isEmpty()) return

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(BUTTON_CORNER_RADIUS),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(BUCKET_CONTENT_PADDING)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Attacker Types",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (!isSubmitted) {
                    Button(
                        onClick = onFillRemaining,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Text("Fill 1x", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
            Spacer(modifier = Modifier.height(BUCKET_SPACING))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(BUCKET_SPACING),
                verticalArrangement = Arrangement.spacedBy(BUCKET_SPACING)
            ) {
                unassigned.forEach { type ->
                    SelectableTypeBadge(
                        type = type,
                        isSelected = selectedType == type,
                        isCorrect = validationResults?.get(type),
                        onClick = {
                            if (!isSubmitted) {
                                if (selectedType == type) onTypeSelect(null)
                                else onTypeSelect(type)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(BUCKET_SPACING))
            Text(
                "Tap a type to select it, then tap a tier to assign it.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun SelectableTypeBadge(
    type: PokemonType,
    isSelected: Boolean,
    isCorrect: Boolean?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(BADGE_WIDTH)
            .then(
                if (isSelected) Modifier.border(
                    SELECTION_BORDER_WIDTH,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(999.dp)
                ) else Modifier
            )
    ) {
        TypeBadge(
            type = type,
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick
        )
        if (isCorrect != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(STATUS_ICON_SIZE)
                    .background(if (isCorrect) VgcLeaf else VgcCoral, CircleShape)
                    .padding(STATUS_ICON_PADDING),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isCorrect) Icons.Rounded.Check else Icons.Rounded.Close,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(STATUS_ICON_INNER_SIZE)
                )
            }
        }
    }
}

@Composable
private fun StreakBadge(streak: Int) {
    val color = if (streak >= 3) VgcLeaf else VgcTeal
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(STREAK_BADGE_CORNER_RADIUS))
            .background(color.copy(alpha = STREAK_BADGE_ALPHA))
            .padding(horizontal = STREAK_BADGE_HORIZONTAL_PADDING, vertical = STREAK_BADGE_VERTICAL_PADDING),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(STREAK_BADGE_DOT_SIZE)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(STREAK_BADGE_SPACING))
        Text(
            text = "Streak $streak",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

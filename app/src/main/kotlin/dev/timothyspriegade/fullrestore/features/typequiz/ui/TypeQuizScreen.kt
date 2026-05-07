@file:OptIn(ExperimentalMaterial3Api::class)

package dev.timothyspriegade.fullrestore.features.typequiz.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.timothyspriegade.fullrestore.core.common.APP_TAGLINE
import dev.timothyspriegade.fullrestore.core.designsystem.AppBottomNav
import dev.timothyspriegade.fullrestore.core.designsystem.AppNavItem
import dev.timothyspriegade.fullrestore.core.designsystem.BOTTOM_SPACER_HEIGHT
import dev.timothyspriegade.fullrestore.core.designsystem.FullRestoreTheme
import dev.timothyspriegade.fullrestore.core.designsystem.MAIN_CONTENT_SPACING
import dev.timothyspriegade.fullrestore.core.designsystem.SCREEN_PADDING_HORIZONTAL
import dev.timothyspriegade.fullrestore.core.designsystem.SCREEN_PADDING_VERTICAL
import dev.timothyspriegade.fullrestore.core.designsystem.StreakBadge
import dev.timothyspriegade.fullrestore.core.designsystem.TypeBadge
import dev.timothyspriegade.fullrestore.core.designsystem.VgcCoral
import dev.timothyspriegade.fullrestore.core.designsystem.VgcLeaf
import dev.timothyspriegade.fullrestore.core.designsystem.VgcTeal
import dev.timothyspriegade.fullrestore.domain.model.Effectiveness
import dev.timothyspriegade.fullrestore.domain.model.PokemonType
import dev.timothyspriegade.fullrestore.domain.model.QuizMode
import dev.timothyspriegade.fullrestore.features.typequiz.viewmodel.TypeQuizViewModel
import kotlinx.coroutines.delay

// Timing constants
const val NEXT_QUEST_DELAY_IN_MILLIS = 1000L
private const val ANIMATION_DURATION_MS = 300

// Layout constants
private val TOP_BAR_ACTION_SPACING = 12.dp

// Card constants
private val CARD_CORNER_RADIUS_LARGE = 28.dp
private val CARD_CORNER_RADIUS_MEDIUM = 24.dp
private val CARD_CONTENT_PADDING = 16.dp

// Mode Selector constants
private val MODE_SELECTOR_PADDING = 14.dp
private val MODE_SELECTOR_SPACING = 8.dp
private val MODE_OPTION_ELEVATION = 2.dp
private val MODE_OPTION_PADDING = 4.dp
private val MODE_OPTION_SPACING = 8.dp

// Question Card constants
private val QUESTION_CARD_HEADER_SPACING = 12.dp
private val QUESTION_CARD_CONTENT_SPACING = 12.dp
private val QUESTION_CARD_LABEL_SPACING = 12.dp
private val QUESTION_CARD_BADGE_SPACING = 8.dp

// Answer Card constants
private val ANSWER_CARD_SPACING = 2.dp
private const val ANSWER_OPTION_ALPHA_CORRECT = 0.2f
private const val ANSWER_OPTION_ALPHA_WRONG = 0.2f
private const val ANSWER_OPTION_ALPHA_HIGHLIGHT = 0.18f
private val ANSWER_OPTION_ELEVATION = 1.dp
private val ANSWER_OPTION_HORIZONTAL_PADDING = 12.dp
private val ANSWER_OPTION_VERTICAL_PADDING = 8.dp
private val ANSWER_OPTION_DOT_SIZE = 10.dp
private val ANSWER_OPTION_SPACING = 12.dp

@Composable
fun TypeQuizRoute(
    navItems: List<AppNavItem>,
    viewModel: TypeQuizViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(state.answeredAtMillis) {
        if (state.answeredAtMillis != null) {
            delay(NEXT_QUEST_DELAY_IN_MILLIS)
            viewModel.nextQuestion()
        }
    }
    TypeQuizScreen(
        mode = state.mode,
        attacker = state.question?.attacker,
        defenderPrimary = state.question?.defenderPrimary,
        defenderSecondary = state.question?.defenderSecondary,
        selectedAnswer = state.selectedAnswer,
        isCorrect = state.isCorrect,
        correctAnswer = state.correctAnswer,
        streak = state.streak,
        onModeChange = viewModel::updateMode,
        onAnswer = viewModel::selectAnswer,
        onNext = viewModel::nextQuestion,
        showNextButton = false,
        navItems = navItems
    )
}

@Composable
fun TypeQuizScreen(
    mode: QuizMode,
    attacker: PokemonType?,
    defenderPrimary: PokemonType?,
    defenderSecondary: PokemonType?,
    selectedAnswer: Effectiveness?,
    isCorrect: Boolean?,
    correctAnswer: Effectiveness?,
    streak: Int,
    onModeChange: (QuizMode) -> Unit,
    onAnswer: (Effectiveness) -> Unit,
    onNext: () -> Unit,
    showNextButton: Boolean,
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
                        Text("Type Chart Quiz", style = MaterialTheme.typography.titleLarge)
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
            AppBottomNav(
                items = navItems
            )
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
                verticalArrangement = Arrangement.spacedBy(MAIN_CONTENT_SPACING)
            ) {
                QuestionCard(
                    attacker = attacker,
                    defenderPrimary = defenderPrimary,
                    defenderSecondary = defenderSecondary
                )
                AnswerCard(
                    selectedAnswer = selectedAnswer,
                    isCorrect = isCorrect,
                    correctAnswer = correctAnswer,
                    onAnswer = onAnswer,
                    onNext = onNext,
                    showNextButton = showNextButton
                )
                ModeSelector(mode = mode, onModeChange = onModeChange)
                Spacer(modifier = Modifier.height(BOTTOM_SPACER_HEIGHT))
            }
        }
    }
}

@Composable
private fun QuestionCard(
    attacker: PokemonType?,
    defenderPrimary: PokemonType?,
    defenderSecondary: PokemonType?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS_LARGE),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(CARD_CONTENT_PADDING)) {
            Text(
                "What is the damage modifier?",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(QUESTION_CARD_HEADER_SPACING))
            Column(verticalArrangement = Arrangement.spacedBy(QUESTION_CARD_CONTENT_SPACING)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Attacker",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(QUESTION_CARD_LABEL_SPACING))
                    if (attacker != null) {
                        TypeBadge(type = attacker)
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Defender",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(QUESTION_CARD_LABEL_SPACING))
                    if (defenderPrimary != null) {
                        TypeBadge(type = defenderPrimary)
                    }
                    if (defenderSecondary != null) {
                        Spacer(modifier = Modifier.width(QUESTION_CARD_BADGE_SPACING))
                        TypeBadge(type = defenderSecondary)
                    }
                }
            }
        }
    }
}

@Composable
private fun AnswerCard(
    selectedAnswer: Effectiveness?,
    isCorrect: Boolean?,
    correctAnswer: Effectiveness?,
    onAnswer: (Effectiveness) -> Unit,
    onNext: () -> Unit,
    showNextButton: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS_LARGE),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(CARD_CONTENT_PADDING),
            verticalArrangement = Arrangement.spacedBy(ANSWER_CARD_SPACING)
        ) {
            Text(
                "Choose the effectiveness",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Effectiveness.ordered.forEach { option ->
                val selected = option == selectedAnswer
                val cardColor = when {
                    isCorrect == true && option == selectedAnswer -> VgcLeaf.copy(alpha = ANSWER_OPTION_ALPHA_CORRECT)
                    isCorrect == false && option == selectedAnswer -> VgcCoral.copy(alpha = ANSWER_OPTION_ALPHA_WRONG)
                    isCorrect == false && option == correctAnswer -> VgcLeaf.copy(alpha = ANSWER_OPTION_ALPHA_HIGHLIGHT)
                    else -> MaterialTheme.colorScheme.surfaceColorAtElevation(ANSWER_OPTION_ELEVATION)
                }
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    onClick = { if (isCorrect == null) onAnswer(option) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = ANSWER_OPTION_HORIZONTAL_PADDING,
                                vertical = ANSWER_OPTION_VERTICAL_PADDING
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(ANSWER_OPTION_DOT_SIZE)
                                .clip(CircleShape)
                                .background(if (selected) VgcTeal else Color.Transparent)
                        )
                        Spacer(modifier = Modifier.width(ANSWER_OPTION_SPACING))
                        Text(
                            option.label,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if (selectedAnswer != null && option == correctAnswer) {
                            Text(
                                "Correct",
                                style = MaterialTheme.typography.labelLarge,
                                color = VgcLeaf
                            )
                        }
                    }
                }
            }
            AnimatedContent(
                targetState = selectedAnswer != null,
                transitionSpec = {
                    fadeIn(animationSpec = tween(ANIMATION_DURATION_MS)) togetherWith
                            fadeOut(animationSpec = tween(ANIMATION_DURATION_MS))
                },
                label = "answer_state"
            ) { answered ->
                if (answered && showNextButton) {
                    Button(
                        onClick = onNext,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Next question")
                    }
                } else {
                    Text(
                        if (answered) "Next question loading..." else "Select an answer to reveal the result.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ModeSelector(mode: QuizMode, onModeChange: (QuizMode) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS_MEDIUM),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(MODE_SELECTOR_PADDING),
            verticalArrangement = Arrangement.spacedBy(MODE_SELECTOR_SPACING)
        ) {
            Text(
                "Training mode",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            ModeOption(
                title = "Single defender",
                subtitle = "1 attacker vs 1 type",
                selected = mode == QuizMode.SINGLE_TYPE,
                onClick = { onModeChange(QuizMode.SINGLE_TYPE) }
            )
            ModeOption(
                title = "Dual defender",
                subtitle = "1 attacker vs 2 types",
                selected = mode == QuizMode.DUAL_TYPE,
                onClick = { onModeChange(QuizMode.DUAL_TYPE) }
            )
            ModeOption(
                title = "Random defenders",
                subtitle = "1 or 2 types each question",
                selected = mode == QuizMode.RANDOM_DEFENDERS,
                onClick = { onModeChange(QuizMode.RANDOM_DEFENDERS) }
            )
        }
    }
}

@Composable
private fun ModeOption(
    title: String,
    subtitle: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val container = if (selected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceColorAtElevation(MODE_OPTION_ELEVATION)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = container),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(MODE_OPTION_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = selected, onClick = onClick)
            Spacer(modifier = Modifier.width(MODE_OPTION_SPACING))
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TypeQuizScreenPreview() {
    FullRestoreTheme(darkTheme = false) {
        TypeQuizScreen(
            mode = QuizMode.SINGLE_TYPE,
            attacker = PokemonType.FIRE,
            defenderPrimary = PokemonType.GRASS,
            defenderSecondary = null,
            selectedAnswer = null,
            isCorrect = null,
            correctAnswer = null,
            streak = 10,
            onModeChange = {},
            onAnswer = {},
            onNext = {},
            showNextButton = false,
            navItems = emptyList()
        )
    }
}

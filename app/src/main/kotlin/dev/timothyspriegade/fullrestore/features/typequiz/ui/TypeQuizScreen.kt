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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.timothyspriegade.fullrestore.core.common.APP_TAGLINE
import dev.timothyspriegade.fullrestore.core.designsystem.TypeBadge
import dev.timothyspriegade.fullrestore.core.designsystem.VgcCoral
import dev.timothyspriegade.fullrestore.core.designsystem.VgcLeaf
import dev.timothyspriegade.fullrestore.core.designsystem.VgcTeal
import dev.timothyspriegade.fullrestore.domain.model.Effectiveness
import dev.timothyspriegade.fullrestore.domain.model.PokemonType
import dev.timothyspriegade.fullrestore.domain.model.QuizMode
import dev.timothyspriegade.fullrestore.features.typequiz.viewmodel.TypeQuizViewModel

@Composable
fun TypeQuizRoute(viewModel: TypeQuizViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
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
        onNext = viewModel::nextQuestion
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
    onNext: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.background
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
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
                    Spacer(modifier = Modifier.width(16.dp))
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ModeSelector(mode = mode, onModeChange = onModeChange)
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
                    onNext = onNext
                )
                TipsCard()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun StreakBadge(streak: Int) {
    val color = if (streak >= 3) VgcLeaf else VgcTeal
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(color.copy(alpha = 0.14f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Streak $streak",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ModeSelector(mode: QuizMode, onModeChange: (QuizMode) -> Unit) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Training mode",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            ModeOption(
                title = "Single defender",
                subtitle = "1 attacker vs 1 type",
                selected = mode == QuizMode.SINGLE_TYPE,
                onClick = { onModeChange(QuizMode.SINGLE_TYPE) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            ModeOption(
                title = "Dual defender",
                subtitle = "1 attacker vs 2 types",
                selected = mode == QuizMode.DUAL_TYPE,
                onClick = { onModeChange(QuizMode.DUAL_TYPE) }
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
        MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = container),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = selected, onClick = onClick)
            Spacer(modifier = Modifier.width(8.dp))
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

@Composable
private fun QuestionCard(
    attacker: PokemonType?,
    defenderPrimary: PokemonType?,
    defenderSecondary: PokemonType?
) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "What is the damage modifier?",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Attacker",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(12.dp))
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
                    Spacer(modifier = Modifier.width(12.dp))
                    if (defenderPrimary != null) {
                        TypeBadge(type = defenderPrimary)
                    }
                    if (defenderSecondary != null) {
                        Spacer(modifier = Modifier.width(8.dp))
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
    onNext: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "Choose the effectiveness",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Effectiveness.ordered.forEach { option ->
                    val selected = option == selectedAnswer
                    val cardColor = when {
                        isCorrect == true && option == selectedAnswer -> VgcLeaf.copy(alpha = 0.2f)
                        isCorrect == false && option == selectedAnswer -> VgcCoral.copy(alpha = 0.2f)
                        isCorrect == false && option == correctAnswer -> VgcLeaf.copy(alpha = 0.18f)
                        else -> MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        onClick = { if (isCorrect == null) onAnswer(option) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(if (selected) VgcTeal else Color.Transparent)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
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
            }
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedContent(
                targetState = selectedAnswer != null,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
                },
                label = "answer_state"
            ) { answered ->
                if (answered) {
                    Button(
                        onClick = onNext,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Next question")
                    }
                } else {
                    Text(
                        "Select an answer to reveal the result.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun TipsCard() {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "Quiz tip",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Remember that dual types stack. A 2x and 0.5x combine into neutral damage.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

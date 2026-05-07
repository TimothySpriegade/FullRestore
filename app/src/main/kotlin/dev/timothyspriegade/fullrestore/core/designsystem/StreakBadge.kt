package dev.timothyspriegade.fullrestore.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

private val STREAK_BADGE_CORNER_RADIUS = 999.dp
private const val STREAK_BADGE_ALPHA = 0.14f
private val STREAK_BADGE_HORIZONTAL_PADDING = 12.dp
private val STREAK_BADGE_VERTICAL_PADDING = 6.dp
private val STREAK_BADGE_DOT_SIZE = 8.dp
private val STREAK_BADGE_SPACING = 8.dp

@Composable
fun StreakBadge(
    streak: Int,
    modifier: Modifier = Modifier
) {
    val color = if (streak >= 3) VgcLeaf else VgcTeal
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(STREAK_BADGE_CORNER_RADIUS))
            .background(color.copy(alpha = STREAK_BADGE_ALPHA))
            .padding(
                horizontal = STREAK_BADGE_HORIZONTAL_PADDING,
                vertical = STREAK_BADGE_VERTICAL_PADDING
            ),
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

package dev.timothyspriegade.fullrestore.core.designsystem

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.timothyspriegade.fullrestore.domain.model.PokemonType

private val BADGE_CORNER_RADIUS = 999.dp
private val BADGE_SHADOW_ELEVATION = 2.dp
private val BADGE_HORIZONTAL_PADDING = 14.dp
private val BADGE_VERTICAL_PADDING = 6.dp

@Composable
fun TypeBadge(
    type: PokemonType,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val colors = typeColorsFor(type)
    Surface(
        onClick = onClick ?: {},
        enabled = onClick != null,
        modifier = modifier,
        color = colors.container,
        contentColor = colors.onContainer,
        shape = RoundedCornerShape(BADGE_CORNER_RADIUS),
        shadowElevation = BADGE_SHADOW_ELEVATION
    ) {
        Text(
            text = type.displayName.uppercase(),
            modifier = Modifier.padding(
                horizontal = BADGE_HORIZONTAL_PADDING,
                vertical = BADGE_VERTICAL_PADDING
            ),
            style = VgcTypography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            softWrap = false
        )
    }
}

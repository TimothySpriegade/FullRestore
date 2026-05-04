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

@Composable
fun TypeBadge(type: PokemonType, modifier: Modifier = Modifier) {
    val colors = typeColorsFor(type)
    Surface(
        modifier = modifier,
        color = colors.container,
        contentColor = colors.onContainer,
        shape = RoundedCornerShape(999.dp),
        shadowElevation = 2.dp
    ) {
        Text(
            text = type.displayName.uppercase(),
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            style = VgcTypography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

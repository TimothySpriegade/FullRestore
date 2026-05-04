package dev.timothyspriegade.fullrestore.core.designsystem

import androidx.compose.ui.graphics.Color
import dev.timothyspriegade.fullrestore.domain.model.PokemonType

data class TypeColorTokens(
    val container: Color,
    val onContainer: Color
)

private val typeColorMap: Map<PokemonType, TypeColorTokens> = mapOf(
    PokemonType.NORMAL to TypeColorTokens(Color(0xFFA8A77A), VgcInk),
    PokemonType.FIRE to TypeColorTokens(Color(0xFFEE8130), Color.White),
    PokemonType.WATER to TypeColorTokens(Color(0xFF6390F0), Color.White),
    PokemonType.ELECTRIC to TypeColorTokens(Color(0xFFF7D02C), VgcInk),
    PokemonType.GRASS to TypeColorTokens(Color(0xFF7AC74C), VgcInk),
    PokemonType.ICE to TypeColorTokens(Color(0xFF96D9D6), VgcInk),
    PokemonType.FIGHTING to TypeColorTokens(Color(0xFFC22E28), Color.White),
    PokemonType.POISON to TypeColorTokens(Color(0xFFA33EA1), Color.White),
    PokemonType.GROUND to TypeColorTokens(Color(0xFFE2BF65), VgcInk),
    PokemonType.FLYING to TypeColorTokens(Color(0xFFA98FF3), Color.White),
    PokemonType.PSYCHIC to TypeColorTokens(Color(0xFFF95587), Color.White),
    PokemonType.BUG to TypeColorTokens(Color(0xFFA6B91A), VgcInk),
    PokemonType.ROCK to TypeColorTokens(Color(0xFFB6A136), VgcInk),
    PokemonType.GHOST to TypeColorTokens(Color(0xFF735797), Color.White),
    PokemonType.DRAGON to TypeColorTokens(Color(0xFF6F35FC), Color.White),
    PokemonType.DARK to TypeColorTokens(Color(0xFF705746), Color.White),
    PokemonType.STEEL to TypeColorTokens(Color(0xFFB7B7CE), VgcInk),
    PokemonType.FAIRY to TypeColorTokens(Color(0xFFD685AD), VgcInk)
)

fun typeColorsFor(type: PokemonType): TypeColorTokens =
    typeColorMap[type] ?: TypeColorTokens(VgcMist, VgcInk)

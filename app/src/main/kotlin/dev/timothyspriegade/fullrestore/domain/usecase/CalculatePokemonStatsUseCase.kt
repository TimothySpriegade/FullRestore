package dev.timothyspriegade.fullrestore.domain.usecase

import dev.timothyspriegade.fullrestore.data.local.pokemon.PokemonEntity
import kotlin.math.floor

class CalculatePokemonStatsUseCase {

    /**
     * Calculates Pokemon stats based on the Pokemon Champions format.
     * 
     * Rules:
     * - Fixed Level 50 & 31 IVs.
     * - Stat Points (SP): Max 32 per stat, 66 total (1 SP = 8 EVs).
     * - Nature: +/- 10% modifier.
     * - Formula: floor((floor(((2 * B + 31) * L) / 100) + 5 + SP) * N)
     */
    fun execute(
        pokemon: PokemonEntity,
        isTailwindActive: Boolean = false,
        isFullInvest: Boolean = false,
        isNatureActive: Boolean = false
    ): HashMap<String, Int> {
        val stats = HashMap<String, Int>()

        if (!isTailwindActive && !isFullInvest && !isNatureActive) {
            stats["hp"] = pokemon.hp
            stats["attack"] = pokemon.attack
            stats["defense"] = pokemon.defense
            stats["spAttack"] = pokemon.spAttack
            stats["spDefense"] = pokemon.spDefense
            stats["speed"] = pokemon.speed
            return stats
        }

        val sp = if (isFullInvest) 32 else 0
        val nature = if (isNatureActive) 1.1 else 1.0

        stats["hp"] = calculateHp(pokemon.hp, sp)
        stats["attack"] = calculateStat(pokemon.attack, sp, nature)
        stats["defense"] = calculateStat(pokemon.defense, sp, nature)
        stats["spAttack"] = calculateStat(pokemon.spAttack, sp, nature)
        stats["spDefense"] = calculateStat(pokemon.spDefense, sp, nature)

        var speed = calculateStat(pokemon.speed, sp, nature)
        if (isTailwindActive) {
            speed *= 2
        }
        stats["speed"] = speed

        return stats
    }

    private fun calculateStat(base: Int, sp: Int, nature: Double): Int {
        val level = 50
        val iv = 31
        // Base formula part: floor(((2 * B + 31) * 50) / 100) -> B + 15
        val basePart = floor(((2 * base + iv) * level) / 100.0).toInt()
        return floor((basePart + 5 + sp) * nature).toInt()
    }

    private fun calculateHp(base: Int, sp: Int): Int {
        val level = 50
        val iv = 31
        // 1 SP = 8 EVs, so EV/4 = 2 * SP
        val evContribution = 2 * sp
        return floor((2 * base + iv + evContribution) * (level / 100.0)).toInt() + level + 10
    }
}

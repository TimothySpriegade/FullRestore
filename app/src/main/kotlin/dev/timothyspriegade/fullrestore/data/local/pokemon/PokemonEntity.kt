package dev.timothyspriegade.fullrestore.data.local.pokemon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey
    @ColumnInfo(name = "pokedex_number") val pokedexNumber: Int,

    val name: String,

    @ColumnInfo(name = "type_1") val type1: String,
    @ColumnInfo(name = "type_2") val type2: String?,

    val hp: Int,
    val attack: Int,
    val defense: Int,

    @ColumnInfo(name = "sp_attack") val spAttack: Int,
    @ColumnInfo(name = "sp_defense") val spDefense: Int,
    val speed: Int,

    @ColumnInfo(name = "base_stat_total") val baseStatTotal: Int,
    @ColumnInfo(name = "height_m") val heightM: Double,
    @ColumnInfo(name = "weight_kg") val weightKg: Double,
    @ColumnInfo(name = "base_experience") val baseExperience: Int?,

    val abilities: String?,
    @ColumnInfo(name = "hidden_ability") val hiddenAbility: String?,
    val generation: String,

    @ColumnInfo(name = "is_legendary") val isLegendary: Boolean,
    @ColumnInfo(name = "is_mythical") val isMythical: Boolean,
    @ColumnInfo(name = "is_baby") val isBaby: Boolean,

    val color: String,
    val shape: String,
    @ColumnInfo(name = "egg_groups") val eggGroups: String,
    val habitat: String?,
    @ColumnInfo(name = "growth_rate") val growthRate: String,
    @ColumnInfo(name = "capture_rate") val captureRate: Int,
    @ColumnInfo(name = "base_happiness") val baseHappiness: Int,
    val genus: String,

    @ColumnInfo(name = "evolution_chain_id") val evolutionChainId: Int,
    @ColumnInfo(name = "flavor_text") val flavorText: String,
    @ColumnInfo(name = "sprite_url") val spriteUrl: String?
)
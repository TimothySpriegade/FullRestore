package dev.timothyspriegade.fullrestore.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import dev.timothyspriegade.fullrestore.R
import dev.timothyspriegade.fullrestore.data.local.pokemon.PokemonDao
import dev.timothyspriegade.fullrestore.data.local.pokemon.PokemonEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "full_restore_database"
                )
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                val dao = getDatabase(context).pokemonDao()
                if (dao.getPokemonCount() > 0) return@launch

                val pokemonList = mutableListOf<PokemonEntity>()
                context.resources.openRawResource(R.raw.pokemon_data_complete).use { inputStream ->
                    csvReader().open(inputStream) {
                        readAllWithHeaderAsSequence().forEach { row ->
                            val pokemon = PokemonEntity(
                                pokedexNumber = row["pokedex_number"]!!.toInt(),
                                name = row["name"]!!,
                                type1 = row["type_1"]!!,
                                type2 = row["type_2"]?.takeIf { it.isNotBlank() },
                                hp = row["hp"]!!.toInt(),
                                attack = row["attack"]!!.toInt(),
                                defense = row["defense"]!!.toInt(),
                                spAttack = row["sp_attack"]!!.toInt(),
                                spDefense = row["sp_defense"]!!.toInt(),
                                speed = row["speed"]!!.toInt(),
                                baseStatTotal = row["base_stat_total"]!!.toInt(),
                                heightM = row["height_m"]!!.toDouble(),
                                weightKg = row["weight_kg"]!!.toDouble(),
                                baseExperience = row["base_experience"]?.toIntOrNull(),
                                abilities = row["abilities"]?.takeIf { it.isNotBlank() },
                                hiddenAbility = row["hidden_ability"]?.takeIf { it.isNotBlank() },
                                generation = row["generation"]!!,
                                isLegendary = row["is_legendary"] == "True",
                                isMythical = row["is_mythical"] == "True",
                                isBaby = row["is_baby"] == "True",
                                color = row["color"]!!,
                                shape = row["shape"]!!,
                                eggGroups = row["egg_groups"]!!,
                                habitat = row["habitat"]?.takeIf { it.isNotBlank() },
                                growthRate = row["growth_rate"]!!,
                                captureRate = row["capture_rate"]!!.toInt(),
                                baseHappiness = row["base_happiness"]!!.toInt(),
                                genus = row["genus"]!!,
                                evolutionChainId = row["evolution_chain_id"]?.toIntOrNull() ?: 0,
                                flavorText = row["flavor_text"]!!,
                                spriteUrl = row["sprite_url"]?.takeIf { it.isNotBlank() }
                            )
                            pokemonList.add(pokemon)
                        }
                    }
                }
                dao.insertAll(pokemonList)
            }
        }
    }
}

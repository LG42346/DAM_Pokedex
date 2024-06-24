package dam.a42346.pokedex.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dam.a42346.pokedex.domain.PokemonDao
import dam.a42346.pokedex.model.Pokemon
import dam.a42346.pokedex.model.PokemonRegion
import dam.a42346.pokedex.model.network.PokemonApi

class PokemonRepository(private val pokemonApi: PokemonApi,
                        private val pokemonDao: PokemonDao) {
    suspend fun getPokemonsByRegion(regionId : Int): LiveData<List<Pokemon>> {
        try {
            val regionWithPokemons = pokemonDao.getPokemonByRegion(regionId)
            if (regionWithPokemons.pokemon.isEmpty()) {
                val pokemons = pokemonApi.fetchPokemonByRegionId(regionId).pokemons.map {
                    val regexToGetId = "/([^/]+)/?\$".toRegex()
                    val pkId = regexToGetId.find(it.url!!)?.value?.removeSurrounding("/")
                    val pkName = it.name ?: ""
                    val pkIdInt = pkId?.toInt() ?: 0
                    val pkImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                            "/sprites/pokemon/other/official-artwork/${pkId}.png"
                    Pokemon(pkIdInt, pkName, pkImageUrl, regionId = regionId)
                }.sortedBy { it.id }
                savePokemonsinDB(pokemons)
                return MutableLiveData(pokemons)
            } else {
                return MutableLiveData(regionWithPokemons.pokemon)
            }
        } catch (e: java.lang.Exception) {
            Log.e("ERROR", e.toString())
        }
        return MutableLiveData()
    }

    private fun savePokemonsinDB(pokemons: List<Pokemon>) {
        pokemons.forEach {
            it.let { it1 -> pokemonDao.insertPokemon(it1) }
        }
    }
}
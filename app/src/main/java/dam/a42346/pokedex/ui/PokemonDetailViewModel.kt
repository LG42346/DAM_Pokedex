package dam.a42346.pokedex.ui

import TypesResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a42346.pokedex.model.Pokemon
import dam.a42346.pokedex.model.PokemonDetail
import dam.a42346.pokedex.model.PokemonStat
import dam.a42346.pokedex.model.PokemonType
import dam.a42346.pokedex.model.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonDetailViewModel : ViewModel() {
    private val _pokemonDetail = MutableLiveData<PokemonDetail?>()
    val pokemonDetail: LiveData<PokemonDetail?>
        get() = _pokemonDetail

    fun fetchPokemonDetailById(pokemonId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val response = NetworkModule.client.fetchPokemonDetailById(pokemonId)
            Log.d("PokemonDetailViewModel", "fetchPokemonDetailById: $response")
            val pkDetail = response.let {
                val poke = Pokemon(pokemonId, "", "")
                val height = response.height ?: 0
                val weight = response.weight ?: 0
                //val types = response.types?.map { it.name } ?: emptyList()
                val types = response.types
                Log.d("PokemonDetailViewModel", "types: $types")
                val typeNames = response.types?.map { it.type?.name } ?: emptyList()
                Log.d("PokemonDetailViewModel", "typeNames: $typeNames")
                val pkTypes = typeNames.map { PokemonType(null, it ?: "None", null, null) }
                Log.d("PokemonDetailViewModel", "pkTypes: $pkTypes")
                /*
                val types = response.types?.map {
                    PokemonType(null, it.name, null, null)
                } ?: emptyList()
                 */
                //val types = emptyList<String>()
                //val stats = response.stats?.map { it.stat.name to it.base_stat } ?: emptyList()
                //val stats = emptyList<String>()
                val stats = response.stats?.map {
                    PokemonStat(it.stat.name, it.baseStat)
                } ?: emptyList()

                PokemonDetail(poke, "", pkTypes, height, weight, stats)

                //val evolutions = emptyList<String>()
                PokemonDetail(poke, "", pkTypes, height, weight, stats,)
            }
            Log.d("PokemonDetailViewModel", "pkDetail: $pkDetail")
            _pokemonDetail.postValue(pkDetail)
        }
    }
}
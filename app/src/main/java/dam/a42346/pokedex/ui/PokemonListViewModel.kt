package dam.a42346.pokedex.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a42346.pokedex.data.PokemonRepository
import dam.a42346.pokedex.model.Pokemon
import dam.a42346.pokedex.model.PokemonRegion
import dam.a42346.pokedex.model.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel() {
    private val _pokemons = MutableLiveData<List<Pokemon>?>()
    val pokemons: LiveData<List<Pokemon>?>
        get() = _pokemons

    private lateinit var _repository: PokemonRepository
    fun initViewMode(repository: PokemonRepository) {
        _repository = repository
    }

    fun fetchPokemons(regionId : Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val pkList = _repository.getPokemonsByRegion(regionId)
            _pokemons.postValue(pkList.value)
        }
    }

    fun fetchPokemonByRegionId(regionId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val response = NetworkModule.client.fetchPokemonByRegionId(regionId)
             val pokemonList = response.pokemons.map {
                 val regexToGetId = "/([^/]+)/?\$".toRegex()
                 val pokemonId = regexToGetId.find(it.url!!)?.value?.removeSurrounding("/")
                 val pokemonName  = it.name.toString().lowercase()
                 val imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                         "/sprites/pokemon/other/official-artwork/$pokemonId.png"
                 Pokemon(pokemonId?.toInt() ?: 0, pokemonName, imgUrl)
             }
            _pokemons.postValue(pokemonList.sortedBy { it.id })
        }
    }
}
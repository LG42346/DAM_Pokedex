package dam.a42346.pokedex.domain

import android.content.Context
import dam.a42346.pokedex.data.PokemonDatabase
import dam.a42346.pokedex.data.PokemonRepository
import dam.a42346.pokedex.data.RegionRepository
import dam.a42346.pokedex.model.network.NetworkModule
import dam.a42346.pokedex.model.network.PokemonApi
class DBModule(private val context:Context) {

    val pokemonClient: PokemonApi
    val regionRepository : RegionRepository
    val pokemonDBManager : PokemonDatabase
    var pokemonRepository: PokemonRepository

    companion object {
        // For Singleton instantiation
        @Volatile private var instance : DBModule ? = null
        fun getInstance (context : Context): DBModule {
            if ( instance != null ) return instance !!
            synchronized ( this ) {
                return DBModule(context)
            }
            return instance!!
        }
    }

    init {
        pokemonClient = NetworkModule.initPokemonRemoteService()
        pokemonDBManager = PokemonDatabase.getInstance(context)
        regionRepository = RegionRepository(pokemonClient,pokemonDBManager.regionDao())
        pokemonRepository = PokemonRepository(pokemonClient,pokemonDBManager.pokemonDao())
    }
}
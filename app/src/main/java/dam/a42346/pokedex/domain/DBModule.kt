package dam.a42346.pokedex.domain

import android.content.Context
import dam.a42346.pokedex.data.PokemonDatabase
import dam.a42346.pokedex.data.PokemonRepository
import dam.a42346.pokedex.data.RegionRepository
import dam.a42346.pokedex.model.network.NetworkModule
import dam.a42346.pokedex.model.network.PokemonApi
class DBModule(context:Context) {

    private val pokemonClient: PokemonApi = NetworkModule.initPokemonRemoteService()
    private val pokemonDBManager : PokemonDatabase = PokemonDatabase.getInstance(context)
    val regionRepository : RegionRepository = RegionRepository(pokemonClient,pokemonDBManager.regionDao())
    var pokemonRepository: PokemonRepository = PokemonRepository(pokemonClient,pokemonDBManager.pokemonDao())

    companion object {
        // For Singleton instantiation
        @Volatile private var instance : DBModule ? = null
        fun getInstance (context : Context): DBModule {
            if ( instance != null ) return instance !!
            synchronized ( this ) {
                return DBModule(context)
            }
            //return instance!!
        }
    }

}
package dam.a42346.pokedex.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dam.a42346.pokedex.domain.PokemonDao
import dam.a42346.pokedex.domain.PokemonRegionDao
import dam.a42346.pokedex.model.Pokemon
import dam.a42346.pokedex.model.PokemonRegion


@Database( entities = [PokemonRegion::class, Pokemon:: class], version = 3, exportSchema = false )
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun regionDao (): PokemonRegionDao

    abstract fun pokemonDao (): PokemonDao
    companion object {
        // For Singleton instantiation
        @Volatile private var instance : PokemonDatabase ? = null
        fun getInstance ( context : Context): PokemonDatabase {
            if ( instance != null ) return instance !!
            synchronized ( this ) {
                instance = Room
                    .databaseBuilder ( context , PokemonDatabase :: class.java , "pokedex_database" )
                    .fallbackToDestructiveMigration ()
                    .build ()
            }
            return instance!!
        }
    }
}
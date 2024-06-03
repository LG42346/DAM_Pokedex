package dam.a42346.pokedex.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dam.a42346.pokedex.domain.PokemonRegionDao
import dam.a42346.pokedex.model.PokemonRegion
import dam.a42346.pokedex.model.network.PokemonApi

class RegionRepository(private val pokemonApi: PokemonApi,
                       private val regionDao: PokemonRegionDao
)
{
    suspend fun getRegions() : LiveData<List<PokemonRegion>>
    {
        if(regionDao.count() > 0) return MutableLiveData(regionDao.getRegions())

        try {
            val regions = pokemonApi.fetchRegionList().results?.map {
                val regexToGetId = "/([^/]+)/?\$".toRegex()
                val regionId = regexToGetId.find(it.url!!)?.value?.removeSurrounding("/")

                //val bgId = resources.getIdentifier(typeName, "bg", packageName)
                //val startersId = resources.getIdentifier(typeName, "starters", packageName)

                PokemonRegion(regionId?.toInt() ?: 0, it.name.toString(),)

            }
            regions?.forEach {
                regionDao.insertRegion(it) }
            return MutableLiveData(regions)

        }catch (e: java.lang.Exception)
        {
            Log.e("ERROR", e.toString())
        }
        return MutableLiveData()
    }
}
package dam.a42346.pokedex.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a42346.pokedex.R
import dam.a42346.pokedex.model.PokemonRegion
import dam.a42346.pokedex.model.mocks.MockData
import dam.a42346.pokedex.model.network.NetworkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegionsViewModel : ViewModel() {
    private val _regions = MutableLiveData<List<PokemonRegion>?>()
    val regions: LiveData<List<PokemonRegion>?>
        get() = _regions

    fun fetchRegions() {
        viewModelScope.launch(Dispatchers.Default) {
            val response = NetworkModule.client.fetchRegionList()
            val regionsList = response.results?.map {
                val regexToGetId = "/([^/]+)/?\$".toRegex()
                val regionId = regexToGetId.find(it.url!!)?.value?.removeSurrounding("/")
                val regionName  = it.name.toString().lowercase()
                val regionDrawable = getRegionDrawable(regionName)
                val regionStarters = getStartersDrawable(regionName)
                PokemonRegion(regionId?.toInt() ?: 0, regionName, regionDrawable, regionStarters)
            }
            _regions.postValue(regionsList)
        }
    }

    private fun getRegionDrawable(regionName: String): Int {
        return when (regionName.lowercase()) {
            "kanto" -> R.drawable.bg_kanto
            "johto" -> R.drawable.bg_johto
            "hoenn" -> R.drawable.bg_hoenn
            "sinnoh" -> R.drawable.bg_sinnoh
            "unova" -> R.drawable.bg_unova
            "kalos" -> R.drawable.bg_kalos
            "alola" -> R.drawable.bg_alola
            "galar" -> R.drawable.bg_galar
            else -> R.drawable.bg_johto
        }
    }
    private fun getStartersDrawable(regionName: String): Int {
        return when (regionName.lowercase()) {
            "kanto" -> R.drawable.pk_kanto
            "johto" -> R.drawable.pk_johto
            "hoenn" -> R.drawable.pk_hoenn
            "sinnoh" -> R.drawable.pk_sinnoh
            "unova" -> R.drawable.pk_unova
            "kalos" -> R.drawable.pk_kalos
            "alola" -> R.drawable.pk_alola
            "galar" -> R.drawable.pk_galar
            else -> R.drawable.pk_johto
        }
    }
}
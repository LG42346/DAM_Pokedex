package dam.a42346.pokedex.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dam.a42346.pokedex.model.PokemonRegion
import dam.a42346.pokedex.model.mocks.MockData

class RegionsViewModel : ViewModel() {
    private val _regions = MutableLiveData<List<PokemonRegion>?>()
    val regions: LiveData<List<PokemonRegion>?>
        get() = _regions

    fun fetchRegions() {
        _regions.value = MockData.regions
    }
}
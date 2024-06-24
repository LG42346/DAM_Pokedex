package dam.a42346.pokedex.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dam.a42346.pokedex.data.PokemonRepository
import dam.a42346.pokedex.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.Gson

class PokemonListViewModel : ViewModel() {
    private val teamListRef = Firebase.database.getReference("teamList")
    val toastMessage = MutableLiveData<String>()

    private val _pokemons = MutableLiveData<List<Pokemon>?>()
    val pokemons: LiveData<List<Pokemon>?>
        get() = _pokemons

    private val _teamList = MutableLiveData<List<Pokemon>>(emptyList())
    val teamList: LiveData<List<Pokemon>>
        get() = _teamList

    private lateinit var _repository: PokemonRepository
    fun initViewMode(repository: PokemonRepository) {
        _repository = repository
    }

    init {
        // Fetch the team list from Firebase when the ViewModel is initialized
        teamListRef.get().addOnSuccessListener { dataSnapshot ->
            val teamListJson = dataSnapshot.getValue(String::class.java)
            Log.d("Firebase", "Team list: $teamListJson")
            if (teamListJson != null) {
                val teamList = Gson().fromJson(teamListJson, Array<Pokemon>::class.java).toList()
                _teamList.value = teamList
            }
        }.addOnFailureListener {
            Log.e("Firebase", "Failed to read value.", it)
        }
        teamListRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val teamListJson = dataSnapshot.getValue(String::class.java)
                if (teamListJson != null) {
                    val teamList = Gson().fromJson(teamListJson, Array<Pokemon>::class.java).toList()
                    _teamList.value = teamList
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "Failed to read value.", databaseError.toException())
            }
        })
    }

    fun fetchPokemons(regionId : Int) {
        viewModelScope.launch(Dispatchers.Default) {
            _pokemons.postValue(_repository.getPokemonsByRegion(regionId).value)
        }
    }

    fun togglePokemonInTeam(pokemon: Pokemon) {
        val currentTeamList = _teamList.value?.toMutableList() ?: mutableListOf()
        if (currentTeamList.contains(pokemon)) {
            currentTeamList.remove(pokemon)
            toastMessage.value = "Pokemon removed from team: ${pokemon.name}"
        } else {
            currentTeamList.add(pokemon)
            toastMessage.value = "Pokemon added from team: ${pokemon.name}"
        }

        teamListRef.setValue(Gson().toJson(currentTeamList))
        toastMessage.value = "All the pokemons are: ${_teamList.value}"
    }
}
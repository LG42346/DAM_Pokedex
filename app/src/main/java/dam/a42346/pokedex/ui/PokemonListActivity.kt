package dam.a42346.pokedex.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import dam.a42346.pokedex.R
import dam.a42346.pokedex.domain.DBModule
import dam.a42346.pokedex.model.Pokemon

interface OnPokemonLongClickListener {
    fun onPokemonLongClick(pokemon: Pokemon)
}

class PokemonListActivity : AppCompatActivity(), OnPokemonLongClickListener {
    private lateinit var viewModel: PokemonListViewModel
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var teamAdapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemon_list)

        pokemonAdapter = PokemonAdapter(pokemonList = emptyList(), context = this, listener = this)
        findViewById<RecyclerView>(R.id.pksRecyclerView).adapter = pokemonAdapter

        teamAdapter = PokemonAdapter(pokemonList = emptyList(), context = this, listener = this)
        findViewById<RecyclerView>(R.id.teamRecyclerView).adapter = teamAdapter

        viewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]
        viewModel.initViewMode(DBModule.getInstance(this).pokemonRepository)
        viewModel.fetchPokemons(intent.getIntExtra("regionId", 2))

        viewModel.pokemons.observe(this) { pokemons ->
            pokemons?.let {
                pokemonAdapter.pokemonList = it
                pokemonAdapter.notifyDataSetChanged()
            }
        }

        viewModel.teamList.observe(this) { team ->
            team?.let {
                teamAdapter.pokemonList = it
                teamAdapter.notifyDataSetChanged()
            }
        }
        viewModel.toastMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPokemonLongClick(pokemon: Pokemon) {
        viewModel.togglePokemonInTeam(pokemon)
    }
}
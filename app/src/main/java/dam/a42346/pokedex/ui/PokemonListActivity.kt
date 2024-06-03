package dam.a42346.pokedex.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import dam.a42346.pokedex.R

class PokemonListActivity : AppCompatActivity() {
    private lateinit var viewModel: PokemonListViewModel
    private val pokemonAdapter = PokemonAdapter(pokemonList = emptyList(), context = this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemon_list)

        findViewById<RecyclerView>(R.id.pksRecyclerView).adapter = pokemonAdapter

        viewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]
        viewModel.fetchPokemonByRegionId(intent.getIntExtra("regionId", 2))
        viewModel.pokemons.observe(this) { pokemons ->
            pokemons?.let {
                pokemonAdapter.pokemonList = it
                pokemonAdapter.notifyDataSetChanged()
            }
        }
    }
}
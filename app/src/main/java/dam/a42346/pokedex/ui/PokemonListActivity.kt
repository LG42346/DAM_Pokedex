package dam.a42346.pokedex.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dam.a42346.pokedex.R
import dam.a42346.pokedex.model.mocks.MockData

class PokemonListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemon_list)
        var listView = findViewById<RecyclerView>(R.id.pksRecyclerView)
        listView.adapter = PokemonAdapter(pokemonList = MockData.pokemons, context = this)
    }
}
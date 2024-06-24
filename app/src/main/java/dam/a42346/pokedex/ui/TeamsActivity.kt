package dam.a42346.pokedex.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dam.a42346.pokedex.R
import dam.a42346.pokedex.domain.DBModule
import dam.a42346.pokedex.model.Pokemon

class TeamsActivity : BottomNavActivity(), OnPokemonLongClickListener {
    private lateinit var teamAdapter: PokemonAdapter
    private lateinit var viewModel: PokemonListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_teams)

        teamAdapter = PokemonAdapter(pokemonList = emptyList(), context = this, listener = this)
        val layout = findViewById<RecyclerView>(R.id.teamRecyclerView)
        layout.layoutManager = LinearLayoutManager(this)
        layout.adapter = teamAdapter

        viewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]
        viewModel.initViewMode(DBModule.getInstance(this).pokemonRepository)
        viewModel.teamList.observe(this) { team ->
            Log.d("TEAM", "team: $team")
            team?.let {
                teamAdapter.pokemonList = it
                //teamAdapter.notifyDataSetChanged()
                teamAdapter.notifyItemRangeChanged(0, it.size)
            }
        }
    }

    override val contentViewId: Int
        get() = R.layout.activity_teams
    override val navigationMenuItemId: Int
        get() = R.id.navigation_teams

    override fun onPokemonLongClick(pokemon: Pokemon) {
    }
}
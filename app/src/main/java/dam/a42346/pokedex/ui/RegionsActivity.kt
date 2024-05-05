package dam.a42346.pokedex.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import dam.a42346.pokedex.R
import dam.a42346.pokedex.databinding.ActivityRegionsBinding
import dam.a42346.pokedex.model.PokemonRegion
import dam.a42346.pokedex.model.mocks.MockData

class RegionsActivity : BottomNavActivity() {
    val viewModel: RegionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val regionBinding = binding as ActivityRegionsBinding

        //var listView = findViewById<RecyclerView>(R.id.regionsRecyclerView)
        val listView = findViewById<RecyclerView>(R.id.regionsRecyclerView)
        //listView.adapter = RegionAdapter(pkRegionList = MockData.regions, context = this)

        viewModel.regions.observe(this) {
            listView.adapter = it?.let { it1 ->
                RegionAdapter(
                    pkRegionList = it1,
                    //...,
                    context = this
                ) { region ->
                    val intent = Intent(this@RegionsActivity, PokemonListActivity::class.java)
                    intent.putExtra("region", region)
                    startActivity(intent)
                }
                /*
            itemClickedListener = OnItemClickedListener { region ->
                val intent = Intent(this, PokemonListActivity::class.java)
                //intent.putExtra("region", region)
                //startActivity(intent)
            }
            */

            }
        }
        viewModel.fetchRegions()
    }

    override val contentViewId: Int
        get() = R.layout.activity_regions
    override val navigationMenuItemId: Int
        get() = R.id.navigation_regions
}
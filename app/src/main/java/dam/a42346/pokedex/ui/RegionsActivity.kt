package dam.a42346.pokedex.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dam.a42346.pokedex.R
import dam.a42346.pokedex.databinding.ActivityRegionsBinding
import dam.a42346.pokedex.domain.DBModule
import dam.a42346.pokedex.model.PokemonRegion
import dam.a42346.pokedex.model.mocks.MockData

class RegionsActivity : BottomNavActivity() {
    private val viewModel: RegionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val regionBinding = binding as ActivityRegionsBinding
        val listView = regionBinding.regionsRecyclerView
        listView.layoutManager = LinearLayoutManager(this)

        viewModel.initViewMode(DBModule.getInstance(this).regionRepository)
        viewModel.regions.observe(this) {
            listView.adapter = it?.let { it1 ->
                RegionAdapter(
                    pkRegionList = it1,
                    context = this
                )
            }
        }
        viewModel.fetchRegions()
    }

    override val contentViewId: Int
        get() = R.layout.activity_regions
    override val navigationMenuItemId: Int
        get() = R.id.navigation_regions
}
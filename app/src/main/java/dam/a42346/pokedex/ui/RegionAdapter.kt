package dam.a42346.pokedex.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import dam.a42346.pokedex.R
import dam.a42346.pokedex.model.PokemonRegion

class RegionAdapter(
    private val pkRegionList: List<PokemonRegion>,
    private val context: Context
) : RecyclerView.Adapter<RegionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bgImageView = itemView.findViewById<AppCompatImageView>(R.id.regionBgImage)
        val startersImageView = itemView.findViewById<AppCompatImageView>(R.id.regionStartersImageView)
        val regionTitleTextView = itemView.findViewById<AppCompatTextView>(R.id.regionNameTextView)
        val regionSubtitleTextView = itemView.findViewById<AppCompatTextView>(R.id.regionIdTextView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_region, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val region = pkRegionList[position]
        holder.bgImageView.setImageResource(region.bg)
        holder.startersImageView.setImageResource(region.starters)
        holder.regionTitleTextView.text = region.name
        //holder.regionSubtitleTextView.text = region.id.toString() + " Generation"
        holder.regionSubtitleTextView.text = buildString {
            append(region.id.toString())
            append(" Generation")
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, PokemonListActivity::class.java)
            intent.putExtra("region", region)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return pkRegionList.size
    }
}
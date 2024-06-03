package dam.a42346.pokedex.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target // conflict with TARGET annotation
import dam.a42346.pokedex.R
import dam.a42346.pokedex.model.Pokemon

class PokemonAdapter(
    var pokemonList: List<Pokemon>,
    private val context: Context
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.cardView)
        val pkImageView = itemView.findViewById<AppCompatImageView>(R.id.pkImage)
        val pkNameTextView = itemView.findViewById<AppCompatTextView>(R.id.pkName)
        val pkIDTextView = itemView.findViewById<AppCompatTextView>(R.id.pkID)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        Glide.with(holder.pkImageView.context)
            .asBitmap()
            .load(pokemon.imageUrl)
            .listener(object : RequestListener<Bitmap>
            {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("TAG", e?.message.toString())
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    p2: Target<Bitmap>?,
                    dataSource: DataSource,
                    p4: Boolean
                ): Boolean {
                    Log.d("TAG", "OnResourceReady")
                    val rgb = Palette.from(resource).generate().lightMutedSwatch?.rgb
                    if (rgb != null) {
                        holder.cardView.setCardBackgroundColor(rgb)
                    }
                    return false
                }
            })
            .into(holder.pkImageView)
        holder.pkNameTextView.text = pokemon.name
        holder.pkIDTextView.text = buildString {
            append("#")
            append(pokemon.id)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PokemonDetailActivity::class.java)
            intent.putExtra("pokemon", pokemon)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}
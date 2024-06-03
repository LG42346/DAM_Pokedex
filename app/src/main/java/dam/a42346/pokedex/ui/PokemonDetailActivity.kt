package dam.a42346.pokedex.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dam.a42346.pokedex.R
import dam.a42346.pokedex.model.Pokemon

class PokemonDetailActivity : AppCompatActivity() {
    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemon_detail)

        val pokemon = intent.getParcelableExtra<Pokemon>("pokemon")

        pokemonDetailViewModel = ViewModelProvider(this)[PokemonDetailViewModel::class.java]
        pokemonDetailViewModel.fetchPokemonDetailById(pokemon?.id ?: 132)
        pokemonDetailViewModel.pokemonDetail.observe(this) { pokemonDetail ->
            Log.d("PokemonDetailActivity", "pokemonDetail: $pokemonDetail")
            val pokemonNameTextView = findViewById<TextView>(R.id.pokemonNameTextView)
            val pokemonImageView = findViewById<ImageView>(R.id.pokemonImageView)
            val pokemonDescriptionTextView = findViewById<TextView>(R.id.pokemonDescriptionTextView)
            val pokemonHeightTextView = findViewById<TextView>(R.id.pokemonHeightTextView)
            val pokemonWeightTextView = findViewById<TextView>(R.id.pokemonWeightTextView)
            val pokemonTypeTextView = findViewById<TextView>(R.id.pokemonTypesTextView)

            pokemonNameTextView.text = pokemon?.name ?: "Unknown"
            //pokemonDescriptionTextView.text = pkDetail?.description ?: "No description available"
            pokemonHeightTextView.text = pokemonDetail?.height?.toString()
            pokemonWeightTextView.text =  pokemonDetail?.weight.toString()
            pokemonTypeTextView.text = pokemonDetail?.types?.joinToString(", ") { it.name } ?: "Unknown"
            Glide.with(this)
                .load(pokemon?.imageUrl)
                .into(pokemonImageView)


            val ll = findViewById<LinearLayout>(R.id.detailLinearLayout)
            //val typesGridLayout = findViewById<GridLayout>(R.id.grid)

            pokemonDetailViewModel.pokemonDetail.observe(this) { pkDetail ->
                pkDetail?.types?.forEach { type ->
                    val typeName = type.name
                    val colorId = resources.getIdentifier(typeName, "color", packageName)
                    val drawableId = resources.getIdentifier(typeName, "drawable", packageName)

                    val typesTextView = TextView(this).apply {
                        text = typeName
                        setTextColor(ContextCompat.getColor(this@PokemonDetailActivity, colorId))
                    }
                    typesTextView.measure(0, 0) // Force measurement of the TextView
                    val drawable = ContextCompat.getDrawable(this, drawableId)?.apply {
                        setBounds(0, 0, typesTextView.measuredHeight, typesTextView.measuredHeight) // Set the bounds to match the text height
                    }
                    typesTextView.setCompoundDrawables(drawable, null, null, null)
                    ll.addView(typesTextView)
                }

                pokemonDetail?.stats?.forEach { stat ->
                    val textView = TextView(this).apply {
                        val statsKV = "${stat.statName}: ${stat.statValue}"
                        text = statsKV
                    }
                    ll.addView(textView)
                }
            }
        }
    }
}
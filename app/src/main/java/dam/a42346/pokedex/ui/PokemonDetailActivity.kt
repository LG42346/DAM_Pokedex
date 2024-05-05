package dam.a42346.pokedex.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import dam.a42346.pokedex.R
import dam.a42346.pokedex.model.Pokemon
import dam.a42346.pokedex.model.mocks.MockData

class PokemonDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemon_detail)

        val pokemon = intent.getParcelableExtra<Pokemon>("pokemon")

        //val pokemonDetail = MockData.pokemonDetail.find { it.pokemon.id == (pokemon?.id ?: 150) }
        val pokemonDetail = MockData.pokemonDetail.find { it.pokemon.id == (pokemon?.id ?: 19) }

        val pokemonNameTextView = findViewById<TextView>(R.id.pokemonNameTextView)
        val pokemonImageView = findViewById<ImageView>(R.id.pokemonImageView)
        val pokemonDescriptionTextView = findViewById<TextView>(R.id.pokemonDescriptionTextView)
        val pokemonHeightTextView = findViewById<TextView>(R.id.pokemonHeightTextView)
        val pokemonWeightTextView = findViewById<TextView>(R.id.pokemonWeightTextView)

        pokemonNameTextView.text = pokemonDetail?.pokemon?.name
        Glide.with(this)
            .load(pokemonDetail?.pokemon?.imageUrl)
            .into(pokemonImageView)
        pokemonDescriptionTextView.text = pokemonDetail?.description
        pokemonHeightTextView.text = pokemonDetail?.height.toString()
        pokemonWeightTextView.text = pokemonDetail?.weight.toString()

        val pokemonTypesTextView = findViewById<TextView>(R.id.pokemonTypesTextView)
        //pokemonTypesTextView.text = pokemonDetail?.types?.joinToString(", ") { it.name }

        pokemonDetail?.types?.forEach { type ->
            val typeTextView = TextView(this)
            typeTextView.text = type.name
            typeTextView.textSize = 16f
            typeTextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            typeTextView.setBackgroundResource(R.drawable.rounded_corners)
            typeTextView.setTextColor(ContextCompat.getColor(this, type.color))
            val drawable = ContextCompat.getDrawable(this, type.icon)
            drawable?.setBounds(0, 0, typeTextView.textSize.toInt(), typeTextView.textSize.toInt())
            typeTextView.setCompoundDrawables(drawable, null, null, null)
            //typeTextView.setCompoundDrawablesWithIntrinsicBounds(type.icon, 0, 0, 0)
            typeTextView.compoundDrawablePadding = 1 // Add padding between the drawable and the text

            val pokemonTypesLinearLayout = findViewById<LinearLayout>(R.id.pokemonTypesLinearLayout)
            pokemonTypesLinearLayout.addView(typeTextView)
        }

    }
}
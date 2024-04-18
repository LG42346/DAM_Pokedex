package dam.a42346.pokedex.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Pokemon(
    var id: Int,
    var name:String,
    var imageUrl: String
) : Parcelable
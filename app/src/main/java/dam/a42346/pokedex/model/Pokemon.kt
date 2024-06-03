package dam.a42346.pokedex.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@kotlinx.parcelize.Parcelize
@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey
    @ColumnInfo(name = "pokemon_id")
    var id: Int,

    @ColumnInfo(name = "pokemon_name")
    var name:String,

    @ColumnInfo(name = "pokemon_imgUrl")
    var imageUrl: String,

    @ColumnInfo(name = "region_id")
    var regionId: Int? = null
) : Parcelable

/*
@kotlinx.parcelize.Parcelize
data class Pokemon(
    var id: Int,
    var name: String,
    var imageUrl: String,
    //val random: PokemonRegion,
    //val toList: List<PokemonType>
) : Parcelable

 */
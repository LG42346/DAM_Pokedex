package dam.a42346.pokedex.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dam.a42346.pokedex.R

@kotlinx.parcelize.Parcelize
@Entity(tableName = "pokemon_region")
data class PokemonRegion(
    @PrimaryKey
    @ColumnInfo(name = "region_id")
    var id: Int,
    @ColumnInfo(name = "region_name")
    var name: String,
    //@ColumnInfo(name = "region_bg")
    //@DrawableRes val bg: Int,
    //@ColumnInfo(name = "region_starters")
    //@DrawableRes val starters: Int
): Parcelable


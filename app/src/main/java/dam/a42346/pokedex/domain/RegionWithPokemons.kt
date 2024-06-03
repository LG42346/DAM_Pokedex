package dam.a42346.pokedex.domain

import androidx.room.Embedded
import androidx.room.Relation
import dam.a42346.pokedex.model.Pokemon
import dam.a42346.pokedex.model.PokemonRegion

data class RegionWithPokemons(
    @Embedded
    val region: PokemonRegion,

    @Relation(
        parentColumn = "region_id",
        entityColumn = "region_id"
    )
    val pokemon: List<Pokemon>
)
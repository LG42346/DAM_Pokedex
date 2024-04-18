package dam.a42346.pokedex.model

data class PokemonDetail(
    var id: Int,
    var pokemon:Pokemon,
    var description: String,
    var height: Int,
    var weight: Int,
    var types: List<PokemonType>,
)
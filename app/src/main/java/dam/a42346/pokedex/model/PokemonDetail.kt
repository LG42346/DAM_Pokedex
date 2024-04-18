package dam.a42346.pokedex.model

data class PokemonDetail(
    var pokemon: Pokemon,
    //var pokemon: String,
    var description: String,
    var types: List<PokemonType>,
    var height: Double,
    var weight: Double,

    var stats: PokemonStats,
    val evolutions: List<PokemonEvolution>,
)
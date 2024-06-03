package dam.a42346.pokedex.model

data class PokemonDetail(
    var pokemon: Pokemon,
    //var pokemon: String,
    var description: String,
    var types: List<PokemonType>,
    //var types: List<String>,
    var height: Int,
    var weight: Int,

    //var stats: PokemonStats,
    //var stats: List<String>,
    var stats: List<PokemonStat>
    //val evolutions: List<PokemonEvolution>,
)
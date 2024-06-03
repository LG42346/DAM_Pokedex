import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//TODO Implement responses: PokemonGenericResponse, PokemonDetailResponse
@JsonClass(generateAdapter = true)
data class PokemonGenericResponse<T>(
    @field:Json(name = "id") val id: Int?,
)
@JsonClass(generateAdapter = true)
data class PokemonDetailResponse(
    @field:Json(name = "height") val height: Int?,
    @field:Json(name = "weight") val weight: Int?,
    @field:Json(name = "types") val types: List<TypesResponse>?,
    @field:Json(name = "stats") val stats: List<StatResponse>?
)
@JsonClass(generateAdapter = true)
data class PokemonListBaseResponse<T>(
    @field:Json(name = "count") val count: Int?,
    @field:Json(name = "next") val next: String?,
    @field:Json(name = "previous") val previous: String?,
    @field:Json(name = "results") val results: List<T>?
)

@JsonClass(generateAdapter = true)
data class PokemonRegionsResponse(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "url") val url: String?
)

@JsonClass(generateAdapter = true)
data class PokemonByRegionResponse(
    @field:Json(name = "pokemon_species") val pokemons: List<PokemonResponse>,
)

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "name") val name: String?,
) {}

@JsonClass(generateAdapter = true)
data class TypeResponse(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "url") val url: String?
)

@JsonClass(generateAdapter = true)
data class TypesResponse(
    @field:Json(name = "slot") val slot: Int?,
    @field:Json(name = "type") val type: TypeResponse?
)

@JsonClass(generateAdapter = true)
data class StatResponse(
    @field:Json(name = "base_stat") val baseStat: Int,
    @field:Json(name = "effort") val effort: Int,
    @field:Json(name = "stat") val stat: StatDetailResponse
)

@JsonClass(generateAdapter = true)
data class StatDetailResponse(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "url") val url: String
)
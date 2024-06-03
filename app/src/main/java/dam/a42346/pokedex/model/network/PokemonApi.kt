package dam.a42346.pokedex.model.network

import PokemonByRegionResponse
import PokemonDetailResponse
import PokemonGenericResponse
import PokemonListBaseResponse
import PokemonRegionsResponse
import dam.a42346.pokedex.model.PokemonDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi
{
    @GET("region")
    suspend fun fetchRegionList(): PokemonListBaseResponse<PokemonRegionsResponse>

    @GET("generation/{id}")
    suspend fun fetchPokemonByRegionId(@Path("id") id:Int): PokemonByRegionResponse


    @GET("pokemon/{id}")
    suspend fun fetchPokemonDetailById(@Path("id") id:Int): PokemonDetailResponse
    ///suspend fun fetchPokemonDetailById(@Path("id") id:Int): PokemonDetailResponse<>

    //@GET("type")
    //suspend fun fetchPokemonTypes(): PokemonListBaseResponse<PokemonGenericResponse>

}
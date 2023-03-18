package com.paulomoura.pokedexxml.model.datasource.service

import com.paulomoura.pokedexxml.model.entity.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon")
    suspend fun getAllPokemons(): List<Pokemon>
    @GET("pokemon/{number}")
    suspend fun getPokemon(@Path("number") number: Int): Pokemon?
}
package com.paulomoura.pokedexxml.model.datasource.service

import com.paulomoura.pokedexxml.model.entity.PokemonDTO
import retrofit2.http.GET

interface PokemonService {
    @GET("pokemon")
    suspend fun getAllPokemons(): List<PokemonDTO>
}
package com.paulomoura.pokedexxml.viewmodel

import androidx.lifecycle.ViewModel
import com.paulomoura.pokedexxml.model.datasource.service.PokemonService
import com.paulomoura.pokedexxml.model.entity.Pokemon
import com.paulomoura.pokedexxml.model.entity.toPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pokemonService: PokemonService) : ViewModel() {

    suspend fun getPokemons(): List<Pokemon> {
        val pokemonDTOs = pokemonService.getAllPokemons()
        return pokemonDTOs.map { it.toPokemon(pokemonDTOs) }
    }
}
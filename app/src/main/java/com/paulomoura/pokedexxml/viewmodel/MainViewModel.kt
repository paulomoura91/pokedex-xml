package com.paulomoura.pokedexxml.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulomoura.pokedexxml.model.datasource.service.PokemonService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pokemonService: PokemonService) : ViewModel() {

    fun getPokemon() {
        viewModelScope.launch {
            try {
                val pokemons = pokemonService.getAllPokemons()
                pokemons.toString()
            } catch (exception: Exception) {
                exception.toString()
            }
        }
    }
}
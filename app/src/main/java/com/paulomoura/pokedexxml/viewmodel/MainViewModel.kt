package com.paulomoura.pokedexxml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulomoura.pokedexxml.extension.assignValue
import com.paulomoura.pokedexxml.model.datasource.service.PokemonService
import com.paulomoura.pokedexxml.model.entity.Pokemon
import com.paulomoura.pokedexxml.model.net.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pokemonService: PokemonService) : ViewModel() {

    private val pokemonsMutableLiveData = MutableLiveData<ApiResponse<List<Pokemon>>>()
    val pokemonsLiveData: LiveData<ApiResponse<List<Pokemon>>> = pokemonsMutableLiveData

    fun getPokemon() {
        pokemonsMutableLiveData.assignValue(ApiResponse.Loading())
        viewModelScope.launch {
            runCatching { pokemonService.getAllPokemons() }
                .onSuccess { pokemonsMutableLiveData.assignValue(ApiResponse.Success(it)) }
                .onFailure { pokemonsMutableLiveData.assignValue(ApiResponse.Error(it)) }
        }
    }
}
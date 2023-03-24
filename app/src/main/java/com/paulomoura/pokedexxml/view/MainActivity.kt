package com.paulomoura.pokedexxml.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.paulomoura.pokedexxml.R
import com.paulomoura.pokedexxml.model.net.ApiResponse
import com.paulomoura.pokedexxml.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listPokemons()
    }

    private fun listPokemons() {
        with(viewModel) {
            pokemonsLiveData.observe(this@MainActivity) { response ->
                when (response) {
                    is ApiResponse.Loading -> ""
                    is ApiResponse.Success -> ""
                    is ApiResponse.Error -> ""
                }
            }
            getPokemon()
        }
    }
}
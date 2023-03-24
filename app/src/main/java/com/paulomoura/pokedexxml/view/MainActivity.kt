package com.paulomoura.pokedexxml.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulomoura.pokedexxml.databinding.ActivityMainBinding
import com.paulomoura.pokedexxml.extension.bindings
import com.paulomoura.pokedexxml.model.entity.Pokemon
import com.paulomoura.pokedexxml.model.net.ApiResponse
import com.paulomoura.pokedexxml.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by bindings(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        listPokemons()
    }

    private fun listPokemons() {
        with(viewModel) {
            pokemonsLiveData.observe(this@MainActivity) { response ->
                when (response) {
                    is ApiResponse.Loading -> showLoadingState()
                    is ApiResponse.Success -> showSuccessState(response.data)
                    is ApiResponse.Error -> showErrorState(response.error)
                }
            }
            getPokemon()
        }
    }

    private fun showLoadingState() { }

    private fun showSuccessState(pokemons: List<Pokemon>?) {
        pokemons?.let {
            with(binding.recyclerViewPokemons) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = PokemonsAdapter(it)
            }
        }
    }

    private fun showErrorState(error: Throwable?) {  }
}
package com.paulomoura.pokedexxml.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
    private var pokemonAdapter: PokemonsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupSearchPokemons()
        listPokemons()
    }

    private fun setupSearchPokemons() {
        with(binding.searchViewPokemons) {
            setOnClickListener { isIconified = false }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    pokemonAdapter?.filter?.filter(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    pokemonAdapter?.filter?.filter(query)
                    return true
                }
            })
        }
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

    private fun showLoadingState() {}

    private fun showSuccessState(pokemons: List<Pokemon>?) {
        pokemons?.let {
            pokemonAdapter = PokemonsAdapter(it.toMutableList())
            with(binding.recyclerViewPokemons) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = pokemonAdapter
            }
        }
    }

    private fun showErrorState(error: Throwable?) {}
}
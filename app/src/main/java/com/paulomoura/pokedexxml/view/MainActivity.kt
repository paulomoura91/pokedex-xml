package com.paulomoura.pokedexxml.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
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
    private var pokemonAdapter: MainAdapter? = null

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
            onBackPressedDispatcher.addCallback(this@MainActivity, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (query.isNullOrEmpty()) finish() else setQuery("", true)
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

    private fun showLoadingState() {
        binding.constraintLayoutLoadingView.isVisible = true
    }

    private fun showSuccessState(pokemons: List<Pokemon>?) {
        binding.constraintLayoutLoadingView.isVisible = false
        pokemons?.let {
            pokemonAdapter = MainAdapter(it.toMutableList()) { pokemon ->  showDetailPokemon(pokemon) }
            with(binding.recyclerViewPokemons) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = pokemonAdapter
            }
        }
    }

    private fun showDetailPokemon(pokemon: Pokemon) {
        startActivity(Intent(this, PokemonActivity::class.java).apply { putExtra(PokemonActivity.POKEMON_EXTRA, pokemon) })
    }

    private fun showErrorState(error: Throwable?) {
        binding.constraintLayoutLoadingView.isVisible = false
        Toast.makeText(this, "Error at showing Pokémons", Toast.LENGTH_SHORT).show()
        Log.e("Pokedex Error", error?.message ?: "")
    }
}
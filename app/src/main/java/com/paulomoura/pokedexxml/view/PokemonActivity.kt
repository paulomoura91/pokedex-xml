package com.paulomoura.pokedexxml.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.paulomoura.pokedexxml.databinding.ActivityPokemonBinding
import com.paulomoura.pokedexxml.extension.bindings
import com.paulomoura.pokedexxml.model.entity.Pokemon

class PokemonActivity : AppCompatActivity() {

    private val binding: ActivityPokemonBinding by bindings(ActivityPokemonBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val pokemon = getPokemon()
        showDetails(pokemon)
    }

    private fun getPokemon() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getParcelableExtra(POKEMON_EXTRA, Pokemon::class.java)
    } else {
        @Suppress("DEPRECATION") intent.getParcelableExtra(POKEMON_EXTRA)
    }

    private fun showDetails(pokemon: Pokemon?) {
        pokemon?.let {
            with(binding) {
                textViewName.text = it.name
                textViewNumber.text = it.number.toString()
                Glide.with(root).load(it.imageUrl).into(imageViewUrl)
                textViewDescription.text = it.description
            }
        }
    }

    companion object {
        const val POKEMON_EXTRA = "pokemon_extra"
    }
}
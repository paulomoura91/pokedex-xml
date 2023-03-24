package com.paulomoura.pokedexxml.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paulomoura.pokedexxml.databinding.RecyclerViewPokemonsRowItemBinding
import com.paulomoura.pokedexxml.model.entity.Pokemon

class PokemonsAdapter(private val pokemons: List<Pokemon>) : RecyclerView.Adapter<PokemonsAdapter.PokemonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = RecyclerViewPokemonsRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun getItemCount() = pokemons.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        with(holder.binding) {
            with(pokemons[position]) {
                textViewNumber.text = number.toString()
                textViewName.text = name
                Glide.with(root).load(imageUrl).into(imageView)
            }
        }
    }

    inner class PokemonViewHolder(val binding: RecyclerViewPokemonsRowItemBinding) : RecyclerView.ViewHolder(binding.root)
}
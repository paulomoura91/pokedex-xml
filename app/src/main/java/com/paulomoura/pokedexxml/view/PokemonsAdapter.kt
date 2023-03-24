package com.paulomoura.pokedexxml.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paulomoura.pokedexxml.databinding.RecyclerViewPokemonsRowItemBinding
import com.paulomoura.pokedexxml.model.entity.Pokemon

class PokemonsAdapter(private val pokemons: MutableList<Pokemon>) : RecyclerView.Adapter<PokemonsAdapter.PokemonViewHolder>() {

    private val initialPokemons = buildList { addAll(pokemons) }
    val filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredPokemons = mutableListOf<Pokemon>()
            if (constraint.isNullOrEmpty()) {
                filteredPokemons.addAll(initialPokemons)
            } else {
                val query = constraint.toString().trim().lowercase()
                if (query.toIntOrNull() == null) {
                    initialPokemons.forEach {if (it.name.lowercase().contains(query)) filteredPokemons.add(it) }
                } else {
                    initialPokemons.forEach {if (it.number.toString().lowercase().contains(query)) filteredPokemons.add(it) }
                }
            }
            return FilterResults().apply { values = filteredPokemons }
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val pokemonResults = results?.values as? MutableList<*>
            pokemonResults?.let {
                pokemons.clear()
                it.forEach { result -> (result as? Pokemon)?.let { pokemon -> pokemons.add(pokemon) } }
                notifyDataSetChanged()
            }
        }
    }

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
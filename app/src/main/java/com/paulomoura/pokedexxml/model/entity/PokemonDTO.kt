package com.paulomoura.pokedexxml.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonDTO(
    val number: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val types: List<String>,
    val evolutions: List<Int>
) : Parcelable

@Parcelize
data class Pokemon(
    val number: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val types: List<String>,
    val evolutions: List<Pokemon>? = null
) : Parcelable

fun PokemonDTO.toEvolution() = Pokemon(
    number = number,
    name = name,
    description = description,
    imageUrl = imageUrl,
    types = types
)

fun PokemonDTO.toPokemon(pokemons: List<PokemonDTO>): Pokemon {
    val filteredEvolutions = pokemons.filter { evolutions.contains(it.number) }.map { it.toEvolution() }
    return Pokemon(
        number = number,
        name = name,
        description = description,
        imageUrl = imageUrl,
        types = types,
        evolutions = filteredEvolutions
    )
}
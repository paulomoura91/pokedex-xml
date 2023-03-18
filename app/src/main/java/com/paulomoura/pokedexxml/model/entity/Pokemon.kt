package com.paulomoura.pokedexxml.model.entity

data class Pokemon(
    val number: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val types: List<String>,
    val evolutions: List<Int>
)

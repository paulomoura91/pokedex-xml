package com.paulomoura.pokedexxml.extension

fun String.intOrString() = toIntOrNull() ?: this
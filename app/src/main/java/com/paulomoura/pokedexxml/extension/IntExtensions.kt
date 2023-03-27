package com.paulomoura.pokedexxml.extension

import android.content.res.Resources

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.toPokemonNumber() = "Nº ${String.format("%03d", this)}"
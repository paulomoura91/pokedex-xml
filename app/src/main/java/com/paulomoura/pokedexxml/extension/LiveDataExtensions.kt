package com.paulomoura.pokedexxml.extension

import android.os.Looper
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.assignValue(value: T) {
    if (isMainThread()) this.value = value else postValue(value)
}

private fun isMainThread() = Looper.myLooper() == Looper.getMainLooper()
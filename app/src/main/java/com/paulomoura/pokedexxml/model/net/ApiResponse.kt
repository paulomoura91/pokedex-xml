package com.paulomoura.pokedexxml.model.net

sealed class ApiResponse<T>(val data: T? = null, val error: Throwable? = null) {
    class Success<T>(data: T) : ApiResponse<T>(data = data)
    class Loading<T> : ApiResponse<T>()
    class Error<T>(error: Throwable) : ApiResponse<T>(error = error)
}
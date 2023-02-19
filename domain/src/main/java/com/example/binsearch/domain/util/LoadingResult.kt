package com.example.binsearch.domain.util

sealed class LoadingResult<out T> {
    data class Success<out T>(val value: T) : LoadingResult<T>()
    data class Error(val message: LoadingError) : LoadingResult<Nothing>()
}

package com.example.binsearch.domain.util

sealed class LoadingState<out T> {
    data class Success<out T>(val value: T) : LoadingState<T>()
    data class Error(val message: ErrorMessage) : LoadingState<Nothing>()
}

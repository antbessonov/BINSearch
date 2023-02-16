package com.example.binsearch.domain.util

sealed interface ErrorMessage

sealed class LoadingError : ErrorMessage {
    object BINNotFound : LoadingError()
    object NetworkProblem : LoadingError()
    object SomethingWentWrong : LoadingError()
}

object OperationFailed : ErrorMessage
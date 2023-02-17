package com.example.binsearch.domain.util

sealed interface ErrorMessage

sealed interface LoadingError : ErrorMessage

object BINNotFound : LoadingError
object NetworkProblem : LoadingError
object SomethingWentWrong : LoadingError
object OperationFailed : ErrorMessage
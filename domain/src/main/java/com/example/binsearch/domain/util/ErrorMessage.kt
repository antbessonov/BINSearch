package com.example.binsearch.domain.util

sealed class ErrorMessage {
    object BINNotFound: ErrorMessage()
    object NetworkProblem: ErrorMessage()
    object SomethingWentWrong: ErrorMessage()
}

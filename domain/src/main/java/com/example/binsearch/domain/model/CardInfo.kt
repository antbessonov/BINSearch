package com.example.binsearch.domain.model

data class CardInfo(
    val numberCard: NumberCard,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: Country,
    val bank: Bank?
)

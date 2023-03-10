package com.example.binsearch.ui.model

data class CardInfoUI(
    val length: String,
    val luhn: String,
    val country: CountryUi,
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: String,
    val bank: BankUi
)
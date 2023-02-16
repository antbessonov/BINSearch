package com.example.binsearch.ui.event

import android.content.Context

sealed interface CardInfoClickedEvent

data class BankUrlClicked(
    val context: Context,
    val bankUrl: String
) : CardInfoClickedEvent

data class BankPhoneClicked(
    val context: Context,
    val bankPhone: String
) : CardInfoClickedEvent

data class CountryCoordinatesClicked(
    val context: Context,
    val countryCoordinates: String
) : CardInfoClickedEvent

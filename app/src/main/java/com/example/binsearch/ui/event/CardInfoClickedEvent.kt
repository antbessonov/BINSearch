package com.example.binsearch.ui.event

sealed interface CardInfoClickedEvent

data class BankUrlClicked(val bankUrl: String) : CardInfoClickedEvent

data class BankPhoneClicked(val bankPhone: String) : CardInfoClickedEvent

data class CountryCoordinatesClicked(val countryCoordinates: String) : CardInfoClickedEvent

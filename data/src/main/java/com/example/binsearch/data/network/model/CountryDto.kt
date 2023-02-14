package com.example.binsearch.data.network.model

data class CountryDto(
    val numeric: String,
    val alpha2: String,
    val name: String,
    val emoji: String,
    val currency: String,
    val latitude: Double,
    val longitude: Double
)
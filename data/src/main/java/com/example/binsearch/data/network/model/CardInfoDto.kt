package com.example.binsearch.data.network.model

import com.google.gson.annotations.SerializedName

data class CardInfoDto(
    @SerializedName("number")
    val numberCard: NumberCardDto,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: CountryDto,
    val bank: BankDto?
)


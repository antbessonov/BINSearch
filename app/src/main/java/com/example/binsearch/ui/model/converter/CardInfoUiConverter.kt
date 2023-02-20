package com.example.binsearch.ui.model.converter

import com.example.binsearch.domain.model.Bank
import com.example.binsearch.domain.model.CardInfo
import com.example.binsearch.domain.model.Country
import com.example.binsearch.ui.model.BankUi
import com.example.binsearch.ui.model.CardInfoUI
import com.example.binsearch.ui.model.CountryUi
import javax.inject.Inject

class CardInfoUiConverter @Inject constructor() {

    fun convertEntityToUiModel(cardInfo: CardInfo): CardInfoUI {
        return CardInfoUI(
            length = cardInfo.numberCard.length?.toString() ?: EMPTY_VALUE,
            luhn = convertBooleanToString(cardInfo.numberCard.luhn),
            country = convertCountryEntityToCountryUiModel(country = cardInfo.country),
            scheme = cardInfo.scheme?.replaceFirstChar(Char::uppercaseChar) ?: EMPTY_VALUE,
            type = convertType(cardInfo.type),
            brand = cardInfo.brand ?: EMPTY_VALUE,
            prepaid = convertBooleanToString(cardInfo.prepaid),
            bank = convertBankEntityToBankUiModel(bank = cardInfo.bank)
        )
    }

    private fun convertBankEntityToBankUiModel(bank: Bank?): BankUi {
        return if (bank == null) {
            BankUi(name = EMPTY_VALUE, url = EMPTY_VALUE, phone = EMPTY_VALUE, city = EMPTY_VALUE)
        } else {
            BankUi(
                name = bank.name ?: EMPTY_VALUE,
                city = bank.city ?: EMPTY_VALUE,
                url = bank.url ?: EMPTY_VALUE,
                phone = bank.phone ?: EMPTY_VALUE
            )
        }
    }

    private fun convertCountryEntityToCountryUiModel(country: Country?): CountryUi {
        return if (country == null) {
            CountryUi(nameWithEmoji = EMPTY_VALUE, latitude = EMPTY_VALUE, longitude = EMPTY_VALUE)
        } else {
            CountryUi(
                nameWithEmoji = "${country.emoji} ${country.name}",
                latitude = country.latitude.toString(),
                longitude = country.longitude.toString()
            )
        }
    }

    private fun convertType(value: String?): String {
        return when (value) {
            "debit" -> DEBIT
            "credit" -> CREDIT
            else -> EMPTY_VALUE
        }
    }

    private fun convertBooleanToString(value: Boolean?) = when (value) {
        true -> YES
        false -> NO
        null -> EMPTY_VALUE
    }

    companion object {
        const val EMPTY_VALUE = "-"
        private const val YES = "Да"
        private const val NO = "Нет"
        private const val DEBIT = "Дебетовая"
        private const val CREDIT = "Кредитная"
    }
}
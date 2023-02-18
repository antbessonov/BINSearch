package com.example.binsearch.ui.model.converter

import com.example.binsearch.domain.model.Bank
import com.example.binsearch.domain.model.CardInfo
import com.example.binsearch.domain.model.Country
import com.example.binsearch.domain.model.NumberCard
import com.example.binsearch.ui.model.BankUi
import com.example.binsearch.ui.model.CardInfoUI
import com.example.binsearch.ui.model.CountryUi
import org.junit.Assert
import org.junit.Test

class CardInfoUiConverterTest {

    @Test
    fun convertEntityToUiModel_CorrectCardInfo_ReturnsCorrectCardInfoUI() {

        val converter = CardInfoUiConverter()

        val cardInfo = CardInfo(
            numberCard = NumberCard(length = 16, luhn = true),
            scheme = "visa",
            type = "credit",
            brand = null,
            prepaid = false,
            country = Country(
                numeric = "840",
                alpha2 = "US",
                name = "United States of America",
                emoji = "🇺🇸",
                currency = "USD",
                latitude = 38.0,
                longitude = -97.0
            ),
            bank = Bank(
                name = "TLC COMMUNITY C.U.",
                url = "www.tlccu.org",
                phone = "(517) 263-9120",
                city = null
            )
        )
        val actual = converter.convertEntityToUiModel(cardInfo = cardInfo)

        val expected = CardInfoUI(
            length = "16",
            luhn = "Да",
            country = CountryUi(
                nameWithEmoji = "🇺🇸 United States of America",
                latitude = "38.0",
                longitude = "-97.0"
            ),
            scheme = "Visa",
            type = "Кредитная",
            brand = "-",
            prepaid = "Нет",
            bank = BankUi(
                name = "TLC COMMUNITY C.U.",
                url = "www.tlccu.org",
                phone = "(517) 263-9120",
                city = "-"
            )
        )

        Assert.assertEquals(expected, actual)
    }
}
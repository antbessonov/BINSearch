package com.example.binsearch.ui.model

import com.example.binsearch.domain.model.CardInfo
import javax.inject.Inject

class CardInfoUiConverter @Inject constructor() {

    fun convertEntityToUiModel(cardInfo: CardInfo): CardInfoUI {
        return CardInfoUI(
            length = cardInfo.numberCard.length.toString(),
            luhn = cardInfo.numberCard.luhn.toString(),
            country = "${cardInfo.country.emoji} ${cardInfo.country.name}",
            scheme = cardInfo.scheme ?: "-",
            type = cardInfo.type ?: "-",
            brand = cardInfo.brand ?: "-",
            prepaid = cardInfo.prepaid.toString(),
            bank = cardInfo.bank.name ?: "-"
        )
    }
}
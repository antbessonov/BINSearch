package com.example.binsearch.ui.state

import com.example.binsearch.domain.util.ErrorMessage
import com.example.binsearch.ui.model.CardInfoUI

data class SearchCardInfoState(
    val isLoadingProgressBar: Boolean = false,
    val binCard: String = "",
    val isBINValid: Boolean = false,
    val cardInfo: CardInfoUI? = null,
    val errorMessage: ErrorMessage? = null
)

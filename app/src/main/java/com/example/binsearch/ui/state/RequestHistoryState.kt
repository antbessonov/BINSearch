package com.example.binsearch.ui.state

import com.example.binsearch.domain.model.BINRequest
import com.example.binsearch.domain.util.SomethingWentWrong

data class RequestHistoryState(
    val binRequestList: List<BINRequest> = emptyList(),
    val errorMessage: SomethingWentWrong? = null
)
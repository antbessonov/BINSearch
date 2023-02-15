package com.example.binsearch.ui.state

import com.example.binsearch.domain.model.BINRequest

data class RequestHistoryState(
    val binRequestList: List<BINRequest> = emptyList()
)
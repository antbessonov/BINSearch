package com.example.binsearch.domain.repository

import com.example.binsearch.domain.model.BINRequest
import com.example.binsearch.domain.model.CardInfo
import com.example.binsearch.domain.util.LoadingResult

interface AppRepository {

    suspend fun getCardInfo(binCard: String): LoadingResult<CardInfo>

    suspend fun getBINRequestList(): List<BINRequest>
}
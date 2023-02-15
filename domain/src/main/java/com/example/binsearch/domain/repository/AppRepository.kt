package com.example.binsearch.domain.repository

import com.example.binsearch.domain.model.BINRequest
import com.example.binsearch.domain.model.CardInfo
import com.example.binsearch.domain.util.LoadingState
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun getCardInfo(binCard: Int): LoadingState<CardInfo>

    fun getBINRequestList(): Flow<List<BINRequest>>
}
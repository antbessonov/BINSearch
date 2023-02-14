package com.example.binsearch.domain.repository

import com.example.binsearch.domain.model.CardInfo
import com.example.binsearch.domain.util.LoadingState

interface AppRepository {

    suspend fun getCardInfo(binCard: Int): LoadingState<CardInfo>
}
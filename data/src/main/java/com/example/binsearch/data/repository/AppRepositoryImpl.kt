package com.example.binsearch.data.repository

import com.example.binsearch.data.mapper.CardInfoMapper
import com.example.binsearch.data.network.APIService
import com.example.binsearch.domain.model.CardInfo
import com.example.binsearch.domain.repository.AppRepository
import com.example.binsearch.domain.util.ErrorMessage
import com.example.binsearch.domain.util.LoadingState
import java.io.IOException

class AppRepositoryImpl(
    private val apiService: APIService,
    private val cardInfoMapper: CardInfoMapper
) : AppRepository {

    override suspend fun getCardInfo(binCard: Int): LoadingState<CardInfo> {
        return try {
            val response = apiService.getGardInfo(binCard = binCard)
            cardInfoMapper.mapResponseToState(response)
        } catch (e: IOException) {
            LoadingState.Error(message = ErrorMessage.NetworkProblem)
        } catch (e: Exception) {
            LoadingState.Error(message = ErrorMessage.SomethingWentWrong)
        }
    }
}
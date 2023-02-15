package com.example.binsearch.data.repository

import com.example.binsearch.data.database.BINRequestDao
import com.example.binsearch.data.database.model.BINRequestDbModel
import com.example.binsearch.data.mapper.BINRequestMapper
import com.example.binsearch.data.mapper.CardInfoMapper
import com.example.binsearch.data.network.APIService
import com.example.binsearch.domain.model.BINRequest
import com.example.binsearch.domain.model.CardInfo
import com.example.binsearch.domain.repository.AppRepository
import com.example.binsearch.domain.util.ErrorMessage
import com.example.binsearch.domain.util.LoadingState
import java.io.IOException

class AppRepositoryImpl(
    private val apiService: APIService,
    private val cardInfoMapper: CardInfoMapper,
    private val binRequestDao: BINRequestDao,
    private val binRequestMapper: BINRequestMapper
) : AppRepository {

    override suspend fun getCardInfo(binCard: String): LoadingState<CardInfo> {
        return try {
            saveBINRequestToDatabase(binCard = binCard)
            val response = apiService.getGardInfo(binCard = binCard)
            cardInfoMapper.mapResponseToState(response)
        } catch (e: IOException) {
            LoadingState.Error(message = ErrorMessage.NetworkProblem)
        } catch (e: Exception) {
            LoadingState.Error(message = ErrorMessage.SomethingWentWrong)
        }
    }

    override suspend fun getBINRequestList(): List<BINRequest> {
        return binRequestDao.getBINRequestList().map {
            binRequestMapper.mapDbModelToEntity(it)
        }
    }


    private suspend fun saveBINRequestToDatabase(binCard: String) {
        val requestTime = cardInfoMapper.getRequestTime()
        binRequestDao.insertBINRequest(
            binRequest = BINRequestDbModel(
                time = requestTime,
                binCard = binCard
            )
        )
    }
}
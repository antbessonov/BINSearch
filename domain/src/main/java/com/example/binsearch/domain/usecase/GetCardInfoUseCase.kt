package com.example.binsearch.domain.usecase

import com.example.binsearch.domain.repository.AppRepository

class GetCardInfoUseCase(
    private val repository: AppRepository
) {

    suspend operator fun invoke(binCard: String) = repository.getCardInfo(binCard = binCard)
}
package com.example.binsearch.domain.usecase

import com.example.binsearch.domain.repository.AppRepository

class GetBINRequestListUseCase(
    private val repository: AppRepository
) {

    suspend operator fun invoke() = repository.getBINRequestList()
}
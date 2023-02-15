package com.example.binsearch.domain.usecase

import com.example.binsearch.domain.repository.AppRepository

class GetBINRequestListUseCase(
    private val repository: AppRepository
) {

    operator fun invoke() = repository.getBINRequestList()
}
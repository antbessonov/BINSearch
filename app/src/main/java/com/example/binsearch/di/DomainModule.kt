package com.example.binsearch.di

import com.example.binsearch.domain.repository.AppRepository
import com.example.binsearch.domain.usecase.GetBINRequestListUseCase
import com.example.binsearch.domain.usecase.GetCardInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideGetCardInfoUseCase(repository: AppRepository): GetCardInfoUseCase {
        return GetCardInfoUseCase(repository = repository)
    }

    @Provides
    fun provideGetBINRequestListUseCase(repository: AppRepository): GetBINRequestListUseCase {
        return GetBINRequestListUseCase(repository = repository)
    }
}
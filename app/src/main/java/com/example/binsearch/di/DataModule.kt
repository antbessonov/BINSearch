package com.example.binsearch.di

import com.example.binsearch.data.mapper.CardInfoMapper
import com.example.binsearch.data.network.APIFactory
import com.example.binsearch.data.network.APIService
import com.example.binsearch.data.repository.AppRepositoryImpl
import com.example.binsearch.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAPIService(): APIService {
        return APIFactory.apiService
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        apiService: APIService,
        cardInfoMapper: CardInfoMapper
    ): AppRepository {
        return AppRepositoryImpl(
            apiService = apiService,
            cardInfoMapper = cardInfoMapper
        )
    }

    @Provides
    @Singleton
    fun provideCardInfoMapper(): CardInfoMapper {
        return CardInfoMapper()
    }
}
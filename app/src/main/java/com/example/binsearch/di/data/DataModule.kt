package com.example.binsearch.di.data

import com.example.binsearch.data.database.BINRequestDao
import com.example.binsearch.data.mapper.BINRequestMapper
import com.example.binsearch.data.mapper.CardInfoMapper
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
    fun provideAppRepository(
        apiService: APIService,
        cardInfoMapper: CardInfoMapper,
        binRequestDao: BINRequestDao,
        binRequestMapper: BINRequestMapper
    ): AppRepository {
        return AppRepositoryImpl(
            apiService = apiService,
            cardInfoMapper = cardInfoMapper,
            binRequestDao = binRequestDao,
            binRequestMapper = binRequestMapper
        )
    }

    @Provides
    @Singleton
    fun provideCardInfoMapper(): CardInfoMapper {
        return CardInfoMapper()
    }

    @Provides
    @Singleton
    fun provideBINRequestMapper(): BINRequestMapper {
        return BINRequestMapper()
    }
}
package com.example.binsearch.di.data

import android.app.Application
import androidx.room.Room
import com.example.binsearch.data.database.AppDatabase
import com.example.binsearch.data.database.BINRequestDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provide(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME).build()
    }

    @Provides
    @Singleton
    fun provideBINRequestDao(appDatabase: AppDatabase): BINRequestDao {
        return appDatabase.binRequestDao()
    }

    private const val DB_NAME = "bin_search.db"
}
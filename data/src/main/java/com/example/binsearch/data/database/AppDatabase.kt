package com.example.binsearch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.binsearch.data.database.model.BINRequestDbModel

@Database(
    entities = [BINRequestDbModel::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun binRequestDao(): BINRequestDao
}
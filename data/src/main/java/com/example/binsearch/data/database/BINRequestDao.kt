package com.example.binsearch.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.binsearch.data.database.model.BINRequestDbModel

@Dao
interface BINRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBINRequest(binRequest: BINRequestDbModel)

    @Query("SELECT * FROM bin_requests ORDER BY time DESC")
    suspend fun getBINRequestList(): List<BINRequestDbModel>
}
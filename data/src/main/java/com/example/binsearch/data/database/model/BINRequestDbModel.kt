package com.example.binsearch.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_requests")
data class BINRequestDbModel(
    @PrimaryKey
    val time: Long,
    val binCard: Int
)

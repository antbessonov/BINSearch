package com.example.binsearch.data.mapper

import com.example.binsearch.data.database.model.BINRequestDbModel
import com.example.binsearch.domain.model.BINRequest
import java.text.SimpleDateFormat
import java.util.*

class BINRequestMapper {

    fun mapDbModelToEntity(dbModel: BINRequestDbModel): BINRequest {
        return BINRequest(time = convertLongToTime(time = dbModel.time), binCard = dbModel.binCard)
    }

    fun getRequestTime(): Long {
        return Date().time
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val pattern = "dd.MM.yyyy HH:mm"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(date)
    }
}
package com.example.binsearch.data.mapper

import com.example.binsearch.data.database.model.BINRequestDbModel
import com.example.binsearch.domain.model.BINRequest

class BINRequestMapper {

    fun mapDbModelToEntity(dbModel: BINRequestDbModel): BINRequest {
        return BINRequest(time = dbModel.time.toString(), binCard = dbModel.binCard)
    }
}
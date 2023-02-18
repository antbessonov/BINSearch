package com.example.binsearch.data.mapper

import com.example.binsearch.data.database.model.BINRequestDbModel
import com.example.binsearch.domain.model.BINRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class BINRequestMapperTest {

    @Test
    fun mapDbModelToEntity_CorrectBINRequestDbModel_ReturnsCorrectBINRequest(){

        val mapper = BINRequestMapper()
        val dbModel = BINRequestDbModel(time = 1676688531152L, binCard = "4256805")
        val actual = mapper.mapDbModelToEntity(dbModel = dbModel)

        val expected = BINRequest(time = "18.02.2023 09:48", binCard = "4256805")

        assertEquals(expected, actual)
    }
}
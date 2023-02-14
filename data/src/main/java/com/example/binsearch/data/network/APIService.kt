package com.example.binsearch.data.network

import com.example.binsearch.data.network.model.CardInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("{binCard}")
    suspend fun getGardInfo(@Path("binCard") binCard: Int): Response<CardInfoDto>
}
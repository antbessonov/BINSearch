package com.example.binsearch.data.mapper

import com.example.binsearch.data.network.model.BankDto
import com.example.binsearch.data.network.model.CardInfoDto
import com.example.binsearch.data.network.model.CountryDto
import com.example.binsearch.data.network.model.NumberCardDto
import com.example.binsearch.domain.model.Bank
import com.example.binsearch.domain.model.CardInfo
import com.example.binsearch.domain.model.Country
import com.example.binsearch.domain.model.NumberCard
import com.example.binsearch.domain.util.BINNotFound
import com.example.binsearch.domain.util.LoadingResult
import com.example.binsearch.domain.util.SomethingWentWrong
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

class CardInfoMapperTest {

    @Test
    fun mapResponseToState_CorrectSuccessResponse_ReturnsSuccessLoadingState() {

        val mapper = CardInfoMapper()

        val code = 200
        val cardInfoDto = CardInfoDto(
            numberCard = NumberCardDto(length = 16, luhn = true),
            scheme = "visa",
            type = "credit",
            brand = null,
            prepaid = false,
            country = CountryDto(
                numeric = "840",
                alpha2 = "US",
                name = "United States of America",
                emoji = "🇺🇸",
                currency = "USD",
                latitude = 38.0,
                longitude = -97.0
            ),
            bank = BankDto(
                name = "TLC COMMUNITY C.U.",
                url = "www.tlccu.org",
                phone = "(517) 263-9120",
                city = null
            )
        )
        val response: Response<CardInfoDto> = Response.success(code, cardInfoDto)
        val actual = mapper.mapResponseToState(response = response)

        val cardInfo = CardInfo(
            numberCard = NumberCard(length = 16, luhn = true),
            scheme = "visa",
            type = "credit",
            brand = null,
            prepaid = false,
            country = Country(
                numeric = "840",
                alpha2 = "US",
                name = "United States of America",
                emoji = "🇺🇸",
                currency = "USD",
                latitude = 38.0,
                longitude = -97.0
            ),
            bank = Bank(
                name = "TLC COMMUNITY C.U.",
                url = "www.tlccu.org",
                phone = "(517) 263-9120",
                city = null
            )
        )
        val expected: LoadingResult<CardInfo> = LoadingResult.Success(cardInfo)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun mapResponseToState_CorrectNotFoundErrorResponse_ReturnsBINNotFoundErrorLoadingState() {

        val mapper = CardInfoMapper()

        val code = 404
        val responseBody: ResponseBody = ResponseBody.create(null, "")
        val response: Response<CardInfoDto> = Response.error(code, responseBody)
        val actual = mapper.mapResponseToState(response = response)

        val expected: LoadingResult<CardInfo> = LoadingResult.Error(message = BINNotFound)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun mapResponseToState_CorrectGoneErrorResponse_ReturnsSomethingWentWrongErrorLoadingState() {

        val mapper = CardInfoMapper()

        val code = 410
        val responseBody: ResponseBody = ResponseBody.create(null, "")
        val response: Response<CardInfoDto> = Response.error(code, responseBody)
        val actual = mapper.mapResponseToState(response = response)

        val expected: LoadingResult<CardInfo> = LoadingResult.Error(message = SomethingWentWrong)

        Assert.assertEquals(expected, actual)
    }
}
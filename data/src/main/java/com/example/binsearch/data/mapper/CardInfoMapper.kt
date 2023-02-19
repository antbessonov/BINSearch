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
import retrofit2.Response

class CardInfoMapper {

    fun mapResponseToState(response: Response<CardInfoDto>): LoadingResult<CardInfo> {
        return when (response.code()) {
            in 200..299 -> {
                val responseBody = response.body() ?: throw Exception("Response body is null.")
                val cardInfo = mapDtoToEntity(responseBody)
                LoadingResult.Success(cardInfo)
            }
            404 -> LoadingResult.Error(message = BINNotFound)
            else -> LoadingResult.Error(message = SomethingWentWrong)
        }
    }

    private fun mapDtoToEntity(dto: CardInfoDto): CardInfo {
        return CardInfo(
            numberCard = mapNumberCardDtoToEntity(numberCardDto = dto.numberCard),
            scheme = dto.scheme,
            type = dto.type,
            brand = dto.brand,
            prepaid = dto.prepaid,
            country = mapCountryDtoToEntity(countryDto = dto.country),
            bank = mapBankDtoToEntity(bankDto = dto.bank)
        )
    }

    private fun mapNumberCardDtoToEntity(numberCardDto: NumberCardDto) = NumberCard(
        length = numberCardDto.length,
        luhn = numberCardDto.luhn
    )

    private fun mapCountryDtoToEntity(countryDto: CountryDto) = Country(
        numeric = countryDto.numeric,
        alpha2 = countryDto.alpha2,
        name = countryDto.name,
        emoji = countryDto.emoji,
        currency = countryDto.currency,
        latitude = countryDto.latitude,
        longitude = countryDto.longitude
    )

    private fun mapBankDtoToEntity(bankDto: BankDto?) = Bank(
        name = bankDto?.name,
        url = bankDto?.url,
        phone = bankDto?.phone,
        city = bankDto?.city
    )
}
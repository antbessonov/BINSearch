package com.example.binsearch.ui.screen.search

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.binsearch.R
import com.example.binsearch.ui.event.BankPhoneClicked
import com.example.binsearch.ui.event.BankUrlClicked
import com.example.binsearch.ui.event.CardInfoClickedEvent
import com.example.binsearch.ui.event.CountryCoordinatesClicked
import com.example.binsearch.ui.model.BankUi
import com.example.binsearch.ui.model.CardInfoUI
import com.example.binsearch.ui.model.CountryUi
import com.example.binsearch.ui.model.converter.CardInfoUiConverter

@Composable
fun CardInfoContent(
    modifier: Modifier,
    cardInfo: CardInfoUI,
    onClickUrl: (CardInfoClickedEvent) -> Unit
) {
    val weightFirst = 0.55f
    val weightSecond = 0.45f

    val context = LocalContext.current

    Card(modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                modifier = modifier.align(alignment = Alignment.CenterHorizontally),
                text = stringResource(id = R.string.card_number_info)
            )
            Row(modifier = modifier.padding(top = 8.dp)) {
                CardInfoDescriptionPart(
                    modifier = modifier.weight(weightFirst),
                    title = stringResource(id = R.string.length),
                    description = cardInfo.length
                )
                CardInfoDescriptionPart(
                    modifier = modifier.weight(weightSecond),
                    title = stringResource(id = R.string.luhn),
                    description = cardInfo.luhn
                )
            }
            Row(modifier = modifier.padding(top = 8.dp)) {
                CardInfoDescriptionPart(
                    modifier = modifier.weight(weightFirst),
                    title = stringResource(id = R.string.network),
                    description = cardInfo.scheme
                )
                CardInfoDescriptionPart(
                    modifier = modifier.weight(weightSecond),
                    title = stringResource(id = R.string.type),
                    description = cardInfo.type
                )
            }
            Row(modifier = modifier.padding(top = 8.dp)) {
                CardInfoDescriptionPart(
                    modifier = modifier.weight(weightFirst),
                    title = stringResource(id = R.string.brand),
                    description = cardInfo.brand
                )
                CardInfoDescriptionPart(
                    modifier = modifier.weight(weightSecond),
                    title = stringResource(id = R.string.prepaid),
                    description = cardInfo.prepaid
                )
            }
            BankInfo(
                modifier = modifier.align(alignment = Alignment.CenterHorizontally),
                bank = cardInfo.bank,
                onClick = onClickUrl,
                context = context
            )
            CountryInfo(
                modifier = modifier.align(alignment = Alignment.CenterHorizontally),
                country = cardInfo.country,
                onClick = onClickUrl,
                context = context
            )
        }
    }
}

@Composable
private fun CountryInfo(
    modifier: Modifier,
    country: CountryUi,
    onClick: (CardInfoClickedEvent) -> Unit,
    context: Context
) {
    Column(modifier = modifier.padding(top = 8.dp)) {
        Text(modifier = modifier, text = stringResource(id = R.string.country))
        Text(
            modifier = modifier,
            text = country.nameWithEmoji,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            modifier = modifier.clickable {
                onClick(
                    CountryCoordinatesClicked(
                        context = context,
                        countryCoordinates = "${country.latitude}, ${country.longitude}"
                    )
                )
            },
            text = String.format(
                stringResource(R.string.сoordinates),
                country.latitude,
                country.longitude
            ),
            color = Color.Blue
        )
    }
}

@Composable
private fun BankInfo(
    modifier: Modifier,
    bank: BankUi,
    onClick: (CardInfoClickedEvent) -> Unit,
    context: Context
) {
    Column(modifier = modifier.padding(top = 8.dp)) {
        Text(modifier = modifier, text = stringResource(R.string.bank))
        Text(
            modifier = modifier,
            text = "${bank.name}, ${bank.city}",
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            modifier = modifier.clickable(enabled = bank.url != CardInfoUiConverter.EMPTY_VALUE) {
                onClick(
                    BankUrlClicked(
                        context = context,
                        bankUrl = bank.url
                    )
                )
            },
            text = bank.url,
            color = Color.Blue
        )
        Text(
            modifier = modifier.clickable(enabled = bank.phone != CardInfoUiConverter.EMPTY_VALUE) {
                onClick(
                    BankPhoneClicked(
                        context = context,
                        bankPhone = bank.phone
                    )
                )
            },
            text = bank.phone,
            color = Color.Blue
        )
    }
}

@Composable
private fun CardInfoDescriptionPart(
    modifier: Modifier,
    title: String,
    description: String
) {
    Column(modifier = modifier) {
        Text(text = title)
        Text(text = description, fontWeight = FontWeight.ExtraBold)
    }
}

@Preview
@Composable
private fun CardInfoContentPreview() {
    val cardInfo = CardInfoUI(
        length = "16",
        luhn = "ДА",
        country = CountryUi(
            nameWithEmoji = "\uD83C\uDDE9\uD83C\uDDF0 Дания",
            latitude = "56",
            longitude = "10"
        ),
        scheme = "Visa",
        type = "Debit",
        brand = "Visa/Dankort",
        prepaid = "Нет",
        bank = BankUi(
            name = "Jyske Bank",
            city = "Hjørring",
            url = "www.jyskebank.dk",
            phone = "+4589893300"
        )
    )
    CardInfoContent(modifier = Modifier, cardInfo = cardInfo, onClickUrl = {})
}
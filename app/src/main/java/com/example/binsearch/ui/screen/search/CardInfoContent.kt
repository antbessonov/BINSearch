package com.example.binsearch.ui.screen.search

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.binsearch.R
import com.example.binsearch.ui.model.BankUi
import com.example.binsearch.ui.model.CardInfoUI
import com.example.binsearch.ui.model.CountryUi
import com.example.binsearch.ui.model.converter.CardInfoUiConverter.Companion.EMPTY_VALUE
import com.example.binsearch.ui.theme.replyTypography
import com.example.binsearch.ui.navigation.goToBrowser
import com.example.binsearch.ui.navigation.goToMapApp
import com.example.binsearch.ui.navigation.goToPhoneApp

@Composable
fun CardInfoContent(
    modifier: Modifier,
    cardInfo: CardInfoUI,
    handleNavigationError: () -> Unit
) {
    val weightFirst = 0.55f
    val weightSecond = 0.45f

    Card(modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                modifier = modifier.align(alignment = Alignment.CenterHorizontally),
                text = stringResource(id = R.string.card_number_info),
                style = replyTypography.titleMedium
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
                handleNavigationError = handleNavigationError
            )
            CountryInfo(
                modifier = modifier.align(alignment = Alignment.CenterHorizontally),
                country = cardInfo.country,
                handleNavigationError = handleNavigationError
            )
        }
    }
}

@Composable
private fun CountryInfo(
    modifier: Modifier,
    country: CountryUi,
    handleNavigationError: () -> Unit
) {
    val context = LocalContext.current

    val isLatitudeEmpty = country.latitude == EMPTY_VALUE
    val isLongitudeEmpty = country.longitude == EMPTY_VALUE

    Column(modifier = modifier.padding(top = 8.dp)) {
        Text(
            modifier = modifier,
            text = stringResource(id = R.string.country),
            style = replyTypography.bodyLarge
        )
        Text(
            modifier = modifier,
            text = country.nameWithEmoji,
            style = replyTypography.bodyMedium
        )
        Text(
            modifier = modifier.clickable(enabled = (!isLatitudeEmpty).or(!isLongitudeEmpty)) {
                goToMapApp(
                    context = context,
                    coordinates = "${country.latitude}, ${country.longitude}",
                    handleNavigationError = handleNavigationError
                )
            },
            text = String.format(
                stringResource(R.string.сoordinates),
                country.latitude,
                country.longitude
            ),
            style = replyTypography.bodyMedium,
            color = if ((!isLatitudeEmpty).or(!isLongitudeEmpty)) {
                Color.Blue
            } else {
                Color.Unspecified
            }
        )
    }
}

@Composable
private fun BankInfo(
    modifier: Modifier,
    bank: BankUi,
    handleNavigationError: () -> Unit
) {
    val context = LocalContext.current

    val isUrlEmpty = bank.url == EMPTY_VALUE
    val isPhoneEmpty = bank.phone == EMPTY_VALUE

    Column(modifier = modifier.padding(top = 8.dp)) {
        Text(
            modifier = modifier,
            text = stringResource(R.string.bank),
            style = replyTypography.bodyLarge
        )
        Text(
            modifier = modifier,
            text = "${bank.name}, ${bank.city}",
            style = replyTypography.bodyMedium
        )
        Text(
            modifier = modifier.clickable(enabled = !isUrlEmpty) {
                goToBrowser(
                    context = context,
                    url = bank.url,
                    handleNavigationError = handleNavigationError
                )
            },
            text = bank.url,
            style = replyTypography.bodyMedium,
            color = if (!isUrlEmpty) {
                Color.Blue
            } else {
                Color.Unspecified
            }
        )
        Text(
            modifier = modifier.clickable(enabled = !isPhoneEmpty) {
                goToPhoneApp(
                    context = context,
                    phone = bank.phone,
                    handleNavigationError = handleNavigationError
                )
            },
            text = bank.phone,
            style = replyTypography.bodyMedium,
            color = if (!isPhoneEmpty) {
                Color.Blue
            } else {
                Color.Unspecified
            }
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
        Text(text = title, style = replyTypography.bodyLarge)
        Text(text = description, style = replyTypography.bodyMedium)
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
    CardInfoContent(modifier = Modifier, cardInfo = cardInfo, handleNavigationError = {})
}
package com.example.binsearch.viewmodel

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binsearch.domain.model.CardInfo
import com.example.binsearch.domain.usecase.GetCardInfoUseCase
import com.example.binsearch.domain.util.LoadingError
import com.example.binsearch.domain.util.LoadingState
import com.example.binsearch.domain.util.OperationFailed
import com.example.binsearch.ui.event.BankPhoneClicked
import com.example.binsearch.ui.event.BankUrlClicked
import com.example.binsearch.ui.event.CardInfoClickedEvent
import com.example.binsearch.ui.event.CountryCoordinatesClicked
import com.example.binsearch.ui.model.converter.CardInfoUiConverter
import com.example.binsearch.ui.state.SearchCardInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCardInfoViewModel @Inject constructor(
    private val getCardInfoUseCase: GetCardInfoUseCase,
    private val cardInfoUiConverter: CardInfoUiConverter
) : ViewModel() {

    private val _searchCardInfoState = MutableStateFlow(SearchCardInfoState())
    val searchCardInfoState = _searchCardInfoState.asStateFlow()

    fun getCardInfo(binCard: String) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
            _searchCardInfoState.value = _searchCardInfoState.value.copy(
                isLoadingProgressBar = false,
                errorMessage = LoadingError.SomethingWentWrong
            )
        }
        viewModelScope.launch(coroutineExceptionHandler) {
            _searchCardInfoState.value =
                _searchCardInfoState.value.copy(isLoadingProgressBar = true)
            delay(1500L)
            val response = getCardInfoUseCase(binCard = binCard)
            handleResponse(response = response)
        }
    }

    fun updateBINCard(enteredValue: String) {
        if (enteredValue.length <= MAX_LENGTH_BIN) {
            val filteredValue = filterEnteredValue(enteredValue = enteredValue)
            checkBINValid(filteredValue = filteredValue)
            _searchCardInfoState.value = _searchCardInfoState.value.copy(
                binCard = filteredValue
            )
        }
    }

    fun hideCardInfo() {
        _searchCardInfoState.value = _searchCardInfoState.value.copy(cardInfo = null)
    }

    fun hideErrorMessage() {
        _searchCardInfoState.value = _searchCardInfoState.value.copy(errorMessage = null)
    }

    fun obtainCardInfoEvent(event: CardInfoClickedEvent) {
        try {
            when (event) {
                is BankPhoneClicked -> reduceEvent(event = event)
                is BankUrlClicked -> reduceEvent(event = event)
                is CountryCoordinatesClicked -> reduceEvent(event = event)
            }
        } catch (e: ActivityNotFoundException) {
            _searchCardInfoState.value = _searchCardInfoState.value.copy(
                errorMessage = OperationFailed
            )
        }
    }

    private fun handleResponse(response: LoadingState<CardInfo>) {
        when (response) {
            is LoadingState.Error -> reduce(response)
            is LoadingState.Success -> reduce(response)
        }
    }

    private fun reduce(loadingState: LoadingState.Error) {
        when (loadingState.message) {
            LoadingError.BINNotFound -> {
                _searchCardInfoState.value = _searchCardInfoState.value.copy(
                    isLoadingProgressBar = false,
                    errorMessage = LoadingError.BINNotFound
                )
            }
            LoadingError.NetworkProblem -> {
                _searchCardInfoState.value = _searchCardInfoState.value.copy(
                    isLoadingProgressBar = false,
                    errorMessage = LoadingError.NetworkProblem
                )
            }
            LoadingError.SomethingWentWrong -> {
                _searchCardInfoState.value = _searchCardInfoState.value.copy(
                    isLoadingProgressBar = false,
                    errorMessage = LoadingError.SomethingWentWrong
                )
            }
        }
    }

    private fun reduce(loadingState: LoadingState.Success<CardInfo>) {
        _searchCardInfoState.value = _searchCardInfoState.value.copy(
            isLoadingProgressBar = false,
            cardInfo = cardInfoUiConverter.convertEntityToUiModel(cardInfo = loadingState.value)
        )
    }

    private fun filterEnteredValue(enteredValue: String) = enteredValue.filter { it.isDigit() }

    private fun checkBINValid(filteredValue: String) {
        val isValid = filteredValue.length in MIN_LENGTH_BIN..MAX_LENGTH_BIN
        _searchCardInfoState.value = _searchCardInfoState.value.copy(isBINValid = isValid)
    }

    private fun reduceEvent(event: BankPhoneClicked) {
        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${event.bankPhone}"))
        event.context.startActivity(callIntent)
    }

    private fun reduceEvent(event: BankUrlClicked) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://${event.bankUrl}"))
        event.context.startActivity(webIntent)
    }

    private fun reduceEvent(event: CountryCoordinatesClicked) {
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${event.countryCoordinates}"))
        event.context.startActivity(mapIntent)
    }

    companion object {
        private const val MIN_LENGTH_BIN = 6
        private const val MAX_LENGTH_BIN = 8
    }
}
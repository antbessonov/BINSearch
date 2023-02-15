package com.example.binsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binsearch.domain.model.CardInfo
import com.example.binsearch.domain.usecase.GetCardInfoUseCase
import com.example.binsearch.domain.util.ErrorMessage
import com.example.binsearch.domain.util.LoadingState
import com.example.binsearch.ui.model.CardInfoUiConverter
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
                errorMessage = ErrorMessage.SomethingWentWrong
            )
        }
        viewModelScope.launch(coroutineExceptionHandler) {
            _searchCardInfoState.value =
                _searchCardInfoState.value.copy(isLoadingProgressBar = true)
            delay(1500L)
            val response = getCardInfoUseCase(binCard = binCard.toInt())
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

    private fun handleResponse(response: LoadingState<CardInfo>) {
        when (response) {
            is LoadingState.Error -> reduce(response)
            is LoadingState.Success -> reduce(response)
        }
    }

    private fun reduce(loadingState: LoadingState.Error) {
        when (loadingState.message) {
            ErrorMessage.BINNotFound -> {
                _searchCardInfoState.value = _searchCardInfoState.value.copy(
                    isLoadingProgressBar = false,
                    errorMessage = ErrorMessage.BINNotFound
                )
            }
            ErrorMessage.NetworkProblem -> {
                _searchCardInfoState.value = _searchCardInfoState.value.copy(
                    isLoadingProgressBar = false,
                    errorMessage = ErrorMessage.NetworkProblem
                )
            }
            ErrorMessage.SomethingWentWrong -> {
                _searchCardInfoState.value = _searchCardInfoState.value.copy(
                    isLoadingProgressBar = false,
                    errorMessage = ErrorMessage.SomethingWentWrong
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

    companion object {
        private const val MIN_LENGTH_BIN = 6
        private const val MAX_LENGTH_BIN = 8
    }
}
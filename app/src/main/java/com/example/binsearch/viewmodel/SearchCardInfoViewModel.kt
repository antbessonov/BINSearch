package com.example.binsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binsearch.domain.model.CardInfo
import com.example.binsearch.domain.usecase.GetCardInfoUseCase
import com.example.binsearch.domain.util.*
import com.example.binsearch.ui.model.converter.CardInfoUiConverter
import com.example.binsearch.ui.state.SearchCardInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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
                errorMessage = SomethingWentWrong
            )
        }
        viewModelScope.launch(coroutineExceptionHandler) {
            _searchCardInfoState.value = _searchCardInfoState.value.copy(
                isLoadingProgressBar = true
            )
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

    fun handleNavigationError() {
        _searchCardInfoState.value = _searchCardInfoState.value.copy(
            errorMessage = OperationFailed
        )
    }

    private fun handleResponse(response: LoadingResult<CardInfo>) {
        when (response) {
            is LoadingResult.Error -> reduce(response)
            is LoadingResult.Success -> reduce(response)
        }
    }

    private fun reduce(loadingResult: LoadingResult.Error) {
        when (loadingResult.message) {
            BINNotFound -> {
                _searchCardInfoState.value = _searchCardInfoState.value.copy(
                    isLoadingProgressBar = false,
                    errorMessage = BINNotFound
                )
            }
            NetworkProblem -> {
                _searchCardInfoState.value = _searchCardInfoState.value.copy(
                    isLoadingProgressBar = false,
                    errorMessage = NetworkProblem
                )
            }
            SomethingWentWrong -> {
                _searchCardInfoState.value = _searchCardInfoState.value.copy(
                    isLoadingProgressBar = false,
                    errorMessage = SomethingWentWrong
                )
            }
        }
    }

    private fun reduce(loadingResult: LoadingResult.Success<CardInfo>) {
        _searchCardInfoState.value = _searchCardInfoState.value.copy(
            isLoadingProgressBar = false,
            cardInfo = cardInfoUiConverter.convertEntityToUiModel(cardInfo = loadingResult.value)
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
package com.example.binsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binsearch.domain.usecase.GetBINRequestListUseCase
import com.example.binsearch.ui.state.RequestHistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestHistoryViewModel @Inject constructor(
    private val getBINRequestListUseCase: GetBINRequestListUseCase
) : ViewModel() {

    private val _requestHistoryState = MutableStateFlow(RequestHistoryState())
    val requestHistoryState = _requestHistoryState.asStateFlow()

    init {
        viewModelScope.launch {
            val binRequestList = getBINRequestListUseCase()
            binRequestList.collect {
                _requestHistoryState.value = _requestHistoryState.value.copy(binRequestList = it)
            }
        }
    }
}
package com.example.binsearch.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.binsearch.R
import com.example.binsearch.domain.util.ErrorMessage
import com.example.binsearch.ui.component.DialogError
import com.example.binsearch.ui.component.ProgressBarLoading
import com.example.binsearch.viewmodel.SearchCardInfoViewModel

@Composable
fun SearchCardInfoScreen(
    modifier: Modifier,
    searchCardInfoViewModel: SearchCardInfoViewModel = hiltViewModel()
) {

    val searchCardInfoState by searchCardInfoViewModel.searchCardInfoState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
    ) {
        SearchField(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.8f)
                .padding(top = 16.dp),
            value = searchCardInfoState.binCard,
            onValueChange = searchCardInfoViewModel::updateBINCard,
            onClickIconButton = searchCardInfoViewModel::getCardInfo,
            onFocusChange = searchCardInfoViewModel::hideCardInfo,
            isBINValid = searchCardInfoState.isBINValid
        )
    }
    ProgressBarLoading(modifier = modifier, isLoading = searchCardInfoState.isLoadingProgressBar)
    searchCardInfoState.errorMessage?.let {
        ErrorMessageContent(
            modifier = modifier,
            onDialogDismiss = searchCardInfoViewModel::hideErrorMessage,
            onButtonClick = searchCardInfoViewModel::hideErrorMessage,
            errorMessage = it
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClickIconButton: (String) -> Unit,
    onFocusChange: () -> Unit,
    isBINValid: Boolean
) {
    val maxChar = 8
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier.onFocusChanged { focusState ->
            if (focusState.isFocused) {
                onFocusChange()
            }
        },
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = stringResource(R.string.bank_identification_number))
        },
        trailingIcon = {
            if ((!isBINValid).and(value.isNotEmpty())) {
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = stringResource(R.string.input_error)
                )
            } else {
                IconButton(
                    enabled = value.isNotEmpty(),
                    onClick = {
                        onClickIconButton(value)
                        focusManager.clearFocus()
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.start_searching)
                    )
                }
            }
        },
        supportingText = {
            if ((!isBINValid).and(value.isNotEmpty())) {
                Text(text = stringResource(R.string.enter_valid_bank_identification_number))
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${value.length} / $maxChar",
                textAlign = TextAlign.End,
            )
        },
        isError = (!isBINValid).and(value.isNotEmpty()), keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(onDone = {
            if (isBINValid.and(value.isNotEmpty())) {
                focusManager.clearFocus()
                onClickIconButton(value)
            }
        }),
        singleLine = true
    )
}

@Composable
private fun ErrorMessageContent(
    modifier: Modifier,
    onDialogDismiss: () -> Unit,
    onButtonClick: () -> Unit,
    errorMessage: ErrorMessage,
) {
    when (errorMessage) {
        ErrorMessage.BINNotFound -> DialogError(
            modifier = modifier,
            onDialogDismiss = onDialogDismiss,
            onButtonClick = onButtonClick,
            errorTitle = stringResource(R.string.BIN_not_found),
            errorDescription = stringResource(R.string.error_BIN_not_found_description),
        )
        ErrorMessage.NetworkProblem -> DialogError(
            modifier = modifier,
            onDialogDismiss = onDialogDismiss,
            onButtonClick = onButtonClick,
            errorTitle = stringResource(R.string.network_problem),
            errorDescription = stringResource(R.string.error_network_problem_description),
        )
        ErrorMessage.SomethingWentWrong -> DialogError(
            modifier = modifier,
            onDialogDismiss = onDialogDismiss,
            onButtonClick = onButtonClick,
            errorTitle = stringResource(R.string.something_went_wrong),
            errorDescription = stringResource(R.string.error_something_went_wrong_description),
        )
    }
}

@Preview
@Composable
private fun SearchFieldPreview() {
    SearchField(
        modifier = Modifier.background(color = Color.White),
        value = "",
        onValueChange = {},
        onClickIconButton = {},
        onFocusChange = {},
        isBINValid = true
    )
}
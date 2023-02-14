package com.example.binsearch.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.binsearch.R

@Composable
fun SearchCardInfoScreen() {

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
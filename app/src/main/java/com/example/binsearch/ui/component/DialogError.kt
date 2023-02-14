package com.example.binsearch.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.binsearch.R

@Composable
fun DialogError(
    modifier: Modifier,
    onDialogDismiss: () -> Unit,
    onButtonClick: () -> Unit,
    errorTitle: String,
    errorDescription: String,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDialogDismiss,
        confirmButton = {
            TextButton(onClick = onButtonClick) {
                Text(text = stringResource(R.string.ok))
            }
        },
        title = {
            Text(text = errorTitle)
        },
        text = {
            Text(text = errorDescription)
        }
    )
}

@Preview
@Composable
private fun DialogErrorPreview() {
    DialogError(
        modifier = Modifier,
        onDialogDismiss = {},
        onButtonClick = {},
        errorTitle = stringResource(R.string.BIN_not_found),
        errorDescription = stringResource(R.string.error_BIN_not_found_description),
    )
}
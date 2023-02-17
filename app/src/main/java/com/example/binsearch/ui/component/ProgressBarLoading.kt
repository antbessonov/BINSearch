package com.example.binsearch.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarLoading(
    modifier: Modifier,
    isLoading: Boolean
) {
    if (isLoading) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                strokeWidth = 5.dp,
                modifier = modifier.size(60.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ProgressBarLoadingPreview() {
    ProgressBarLoading(isLoading = true, modifier = Modifier)
}
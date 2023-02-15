package com.example.binsearch.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.binsearch.R
import com.example.binsearch.domain.model.BINRequest
import com.example.binsearch.ui.component.DialogError
import com.example.binsearch.viewmodel.RequestHistoryViewModel

@Composable
fun RequestHistoryScreen(
    modifier: Modifier,
    requestHistoryViewModel: RequestHistoryViewModel = hiltViewModel()
) {
    val requestHistoryState by requestHistoryViewModel.requestHistoryState.collectAsStateWithLifecycle()

    BinRequestList(modifier = modifier, binRequestList = requestHistoryState.binRequestList)
    if (requestHistoryState.isErrorMessage) {
        DialogError(
            modifier = modifier,
            onDialogDismiss = requestHistoryViewModel::hideErrorMessage,
            onButtonClick = requestHistoryViewModel::hideErrorMessage,
            errorTitle = stringResource(R.string.something_went_wrong),
            errorDescription = stringResource(R.string.error_something_went_wrong_description),
        )
    }
}

@Composable
private fun BinRequestList(
    modifier: Modifier,
    binRequestList: List<BINRequest>
) {
    LazyColumn {
        items(binRequestList) { binRequest ->
            BinRequestItem(modifier = modifier, binRequest = binRequest)
        }
    }
}

@Composable
private fun BinRequestItem(
    modifier: Modifier,
    binRequest: BINRequest
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Column(modifier = modifier.padding(8.dp).fillMaxWidth()) {
            Text(
                modifier = modifier.padding(start = 8.dp),
                text = binRequest.binCard,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp
            )
            Text(modifier = modifier.align(alignment = Alignment.End), text = binRequest.time)
        }
    }
}

@Preview
@Composable
private fun BinRequestItemPreview() {
    val binRequest = BINRequest(time = "14:35 11.12.21", binCard = "99778")
    BinRequestItem(modifier = Modifier, binRequest = binRequest)
}

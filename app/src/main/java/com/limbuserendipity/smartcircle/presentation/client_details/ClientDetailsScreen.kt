package com.limbuserendipity.smartcircle.presentation.client_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.limbuserendipity.smartcircle.data.core.Action
import com.limbuserendipity.smartcircle.data.core.model.Client
import com.limbuserendipity.smartcircle.presentation.component.Circle
import com.limbuserendipity.smartcircle.presentation.component.Space
import limbuserendipity.smartcircle.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDetailsScreen(viewModel: ClientDetailsViewModel) {

    val state =
        viewModel.state.collectAsStateWithLifecycle().value

    val client = state.client

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(client.id)
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.backClicked() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }) { innerPadding ->
        ClientDetailsContent(
            state.client,
            innerPadding
        )
    }
}

@Composable
private fun ClientDetailsContent(
    client : Client,
    innerPadding: PaddingValues
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {

        Circle(
            isConnect = true
        ) {

        }

        24.dp.Space()

        client.messages.forEach { message ->

            val text = if(message.type == Action.time) formatUnixTime(message.text.toLong()) else message.value

            Text(
                text = text
            )
        }


    }
}
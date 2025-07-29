package com.limbuserendipity.smartcircle.presentation.home

import ServerStatus
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.limbuserendipity.smartcircle.data.core.Action
import com.limbuserendipity.smartcircle.data.core.model.Client
import com.limbuserendipity.smartcircle.presentation.component.ClientCard

@Composable
fun HomeScreen(
    viewModel : HomeViewModel
){

    val state by viewModel.state.collectAsState()
    HomeContent(
        status = state.serverStatus,
        clients = state.clients,
        onLightClick = { client ->
            viewModel.sendToClient(client.ip, Action.blink.request())
        },
        onDetailsClick = { id ->
            viewModel.toDetailsClient(id)
        }
    )

}


@Composable
fun HomeContent(
    status : ServerStatus,
    clients : List<Client>,
    onLightClick : (Client) -> Unit,
    onDetailsClick : (String) -> Unit
){

    val statusText = when(status){
        ServerStatus.Running -> "Running"
        ServerStatus.Stopped -> "Stopped"
        is ServerStatus.Error -> "error: ${status.message}"
        else -> {""}
    }

    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .padding(8.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = statusText)
            }
        }

        itemsIndexed(clients){ index, client ->
            ClientCard(
                number = index.toString(),
                id = client.id,
                isConnect = true, //client.status == ArduinoStatus.Connected,
                onLightClick = { onLightClick(client) },
                onDetailsClick = { onDetailsClick(client.id) }
            )
        }
    }
}

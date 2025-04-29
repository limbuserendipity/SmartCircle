package com.limbuserendipity.smartcircle.presentation.home

import ServerStatus
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.limbuserendipity.smartcircle.data.core.model.Arduino
import com.limbuserendipity.smartcircle.data.core.model.ArduinoStatus
import com.limbuserendipity.smartcircle.presentation.component.ClientCard

@Composable
fun HomeScreen(
    viewModel : HomeViewModel
){

    val state by viewModel.state.collectAsState()
    SearchContent(
        status = state.serverStatus,
        clients = state.arduinos,
        onLightClick = { client ->
            viewModel.sendToClient(client.ip, "light")
        }
    )

    LaunchedEffect(Unit) {
        viewModel.startServer()
    }

}


@Composable
fun SearchContent(
    status : ServerStatus,
    clients : List<Arduino>,
    onLightClick : (Arduino) -> Unit
){

    val statusText = when(status){
        ServerStatus.Running -> "Running"
        ServerStatus.Stopped -> "Stopped"
        is ServerStatus.Error -> "error: ${status.message}"
    }

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .padding(32.dp)
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
                ip = client.ip,
                time = client.time,
                massage = client.massage,
                isConnect = client.status == ArduinoStatus.Connected,
                onLightClick = { onLightClick(client) }
            )
        }
    }
}

package com.limbuserendipity.smartcircle.presentation.client_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.limbuserendipity.smartcircle.data.core.WebSocketServer
import com.limbuserendipity.smartcircle.data.core.model.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import com.limbuserendipity.smartcircle.presentation.navigator.AppNavigator
import com.limbuserendipity.smartcircle.presentation.navigator.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ClientDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val server : WebSocketServer,
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val route = savedStateHandle.toRoute<Screen.ClientDetails>()
    private val _state =
        MutableStateFlow(getInitClient())
    val state =
        _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            server.client.collect{ current ->
                if(current.id == route.id){
                    _state.update { state ->
                        state.copy(client = current)
                    }
                }
            }
        }

        getClientDetails(route.id)
    }

    fun backClicked() {
        appNavigator.navigateBack()
    }

    private fun getClientDetails(id: String) {
/*        viewModelScope.launch(Dispatchers.IO) {
            _state.update { state ->
                state.copy(
                    client = server.client.replayCache.first { client ->
                        client.id == id
                    }
                )
            }
        }*/
    }

    private fun getInitClient() = ClientDetailsViewState(
        client = Client(ip = "0")
    )
}

fun formatUnixTime(unixTime: Long): String {
    val instant = Instant.ofEpochSecond(unixTime)
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        .withZone(ZoneId.systemDefault()) // Используем текущую временную зону

    return formatter.format(instant)
}
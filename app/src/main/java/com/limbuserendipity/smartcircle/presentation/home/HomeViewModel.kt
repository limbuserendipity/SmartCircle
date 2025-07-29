package com.limbuserendipity.smartcircle.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.limbuserendipity.smartcircle.data.core.WebSocketServer
import com.limbuserendipity.smartcircle.data.core.model.Client
import com.limbuserendipity.smartcircle.presentation.navigator.AppNavigator
import com.limbuserendipity.smartcircle.presentation.navigator.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val server : WebSocketServer,
    private val appNavigator: AppNavigator
) : ViewModel() {


    // may be use setter?
    private var _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            server.client.collect { newClient ->
                _state.update { state ->
                    val newList = state.clients.toMutableList()
                    val client = newList.firstOrNull { client ->
                        client.ip == newClient.ip
                    }
                    if(client == null){
                        newList.add(newClient)
                        Log.v("Porno", "null")
                    }else{
                        Log.v("Porno", "not null")
                        val index = newList.indexOf(client)
                        newList[index] = client.copy(
                            id = newClient.id,
                            messages = newClient.messages
                        )
                    }
                    state.copy(clients = newList)
                }
            }
        }
    }


    private inline fun updateState(transform: HomeState.() -> HomeState) {
        _state.update { it.transform() }
    }

    private inline fun updateClientsList(transform: (List<Client>) -> List<Client>) {
        updateState { copy(clients = transform(clients)) }
    }

    fun sendToClient(ip: String, message: String) {
        viewModelScope.launch {
            server.send(ip, message)
        }
    }

    fun getCurrentTime(): String {
        val currentTime = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        return currentTime.format(formatter)
    }

    fun toDetailsClient(id : String){
        appNavigator.navigateTo(Screen.ClientDetails(id))
    }

}
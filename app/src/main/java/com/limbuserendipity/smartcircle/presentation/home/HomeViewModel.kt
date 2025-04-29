package com.limbuserendipity.smartcircle.presentation.home

import WebSocketServer
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.limbuserendipity.smartcircle.data.core.model.Arduino
import com.limbuserendipity.smartcircle.data.core.model.ArduinoStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.jmdns.JmDNS
import javax.jmdns.ServiceListener
import javax.jmdns.ServiceEvent
import okhttp3.*
import java.net.InetAddress
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    val server = WebSocketServer()

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        setupServerCallbacks()
    }

    private inline fun updateState(transform: HomeState.() -> HomeState) {
        _state.update { it.transform() }
    }

    private inline fun updateArduinosList(transform: (List<Arduino>) -> List<Arduino>) {
        updateState { copy(arduinos = transform(arduinos)) }
    }

    private fun setupServerCallbacks() {
        server.apply {
            onServerStarted = ::handleServerStarted
            onServerStopped = ::handleServerStopped
            onClientConnected = ::handleClientConnected
            onClientDisconnected = ::handleClientDisconnected
            onMessageReceived = ::handleMessageReceived
        }
    }

    private fun handleServerStarted() {
        updateState { copy(serverStatus = ServerStatus.Running) }
    }

    private fun handleServerStopped() {
        updateState { copy(serverStatus = ServerStatus.Stopped) }
    }


    private fun handleClientConnected(ip: String) {
        updateArduinosList { arduinos ->
            if (arduinos.any { it.ip == ip }) {
                // Устройство уже существует - обновляем статус
                arduinos.map { arduino ->
                    if (arduino.ip == ip) {
                        arduino.copy(
                            status = ArduinoStatus.Connected,
                            time = getCurrentTime()
                        )
                    } else {
                        arduino
                    }
                }
            } else {
                // Новое устройство - добавляем в список
                arduinos + Arduino(
                    ip = ip,
                    status = ArduinoStatus.Connected,
                    time = getCurrentTime()
                )
            }
        }
    }

    private fun handleClientDisconnected(ip: String) {
        updateArduinosList { arduinos ->
            arduinos.map { arduino ->
                if (arduino.ip == ip) {
                    arduino.copy(status = ArduinoStatus.Disabled)
                } else {
                    arduino
                }
            }
        }
    }

    private fun handleMessageReceived(ip: String, message: String) {
        updateArduinosList { arduinos ->
            arduinos.map { arduino ->
                if (arduino.ip == ip) {
                    arduino.copy(massage = message)
                } else {
                    arduino
                }
            }
        }
    }

    fun sendToClient(ip: String, message: String){
        viewModelScope.launch {
            server.sendToClient(ip,message)
        }
    }

    fun startServer() {
        registerService()
        viewModelScope.launch {
            try {
                server.start()
            } catch (e: Exception) {
                updateState { copy(serverStatus = ServerStatus.Error(e.message.orEmpty())) }
            }
        }
    }

    fun registerService() {
        val shutdownChannel = Channel<Unit>(capacity = Channel.RENDEZVOUS)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val jmdns = JmDNS.create(InetAddress.getLocalHost())

                    val serviceInfo = javax.jmdns.ServiceInfo.create(
                        "anime._tcp.local.", // тип сервиса
                        "KtorServer",        // имя сервиса
                        8080,                // порт
                        "path=/"            // дополнительные данные
                    )

                    jmdns.registerService(serviceInfo)
                    Log.w("lolkek", "Service registered")

                    // Регистрируем обработчик закрытия в том же scope
                    launch {
                        try {
                            shutdownChannel.receive()
                            // Здесь можно добавить ожидание сигнала завершения
                            // Например, через Flow или Channel
                        } finally {
                            withContext(Dispatchers.IO) {
                                jmdns.unregisterAllServices()
                                jmdns.close()
                                Log.w("lolkek", "Service unregistered")
                            }
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    // Создаем JmDNS
    fun searchIp() {
        viewModelScope.launch {
            Log.w("lolkek", "start search")
            withContext(Dispatchers.IO){
                val jmDNS: JmDNS = JmDNS.create()
                // Ищем все сервисы типа "_arduino._tcp.local."
                jmDNS.addServiceListener("anime._tcp.local.", object : ServiceListener {
                    override fun serviceAdded(event: ServiceEvent?) {
                        // Устройство найдено
                        val ip = event?.info?.hostAddresses?.first()
                        if (ip != null)
                            with(_state) {
                                val ars = value.arduinos.toMutableList()
                                ars.add(
                                    Arduino(
                                        ip = ip
                                    )
                                )
                                //update(value.copy(arduinos = ars))
                            }

                        println("Найдено Arduino: $ip")
                        Log.w("lolkek", "Найдено Arduino: $ip")
                    }

                    override fun serviceRemoved(event: ServiceEvent?) {
                        TODO("Not yet implemented")
                    }

                    override fun serviceResolved(event: ServiceEvent?) {
                        Log.w("lolkek", "Найдено Arduino:")
                    }
                })
            }
        }
    }

    fun getCurrentTime(): String {
        val currentTime = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        return currentTime.format(formatter)
    }

}
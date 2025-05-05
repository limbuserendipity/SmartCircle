package com.limbuserendipity.smartcircle.domain.server

interface AppServer {
    var callback : AppServerCallback

    fun start()
    fun stop()
    suspend fun broadcastMessage(message: String)
    suspend fun sendToClient(ip: String, message: String) : Boolean

}

data class AppServerCallback(
    var onServerStarted: (() -> Unit),
    var onServerStopped: (() -> Unit),
    var onClientConnected: ((ip: String) -> Unit),
    var onClientDisconnected: ((ip: String) -> Unit),
    var onMessageReceived: ((ip: String, message: String) -> Unit),
    var onError: ((Throwable) -> Unit)
)
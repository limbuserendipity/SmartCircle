package com.limbuserendipity.smartcircle.data.core

import android.util.Log
import com.limbuserendipity.smartcircle.data.core.Action.id
import com.limbuserendipity.smartcircle.data.core.model.Client
import io.ktor.server.application.install
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.origin
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.Duration
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

interface WebSocketServer {
    val client: SharedFlow<Client>
    suspend fun send(ip: String, request: String)
}

@Singleton
class WebSocketServerImpl @Inject constructor(
    val port : Int,
    val pingInterval: Duration,
    val timeoutDuration: Duration,
    val job: CompletableJob
) : WebSocketServer, CoroutineScope {

    override val coroutineContext = job + Dispatchers.IO

    private val sessions = ConcurrentHashMap<String, WebSocketSession>()
    private val _client = MutableSharedFlow<Client>(
        replay = 64,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val client : SharedFlow<Client> = _client.asSharedFlow()

    private var server: ApplicationEngine? = null

    init {
        start()
    }

    fun start() {
        try {
            server = embeddedServer(Netty, port = port) {
                install(WebSockets) {
                    pingPeriod = pingInterval
                    timeout = timeoutDuration
                }
                routing {
                    webSocket("/") {
                        val ip = call.request.origin.remoteHost
                        try {
                            registerSession(ip)
                            handleIncomingFrames(ip)
                        } catch (e: ClosedReceiveChannelException) {
                            Log.d("WebSocket", "Client $ip disconnected normally")
                        } catch (e: Exception) {
                            handleError(e, "Error in WebSocket connection for $ip")
                        } finally {
                            unregisterSession(ip)
                        }
                    }
                }
            }.start(wait = false)

        } catch (e: Exception) {
            handleError(e, "Failed to start WebSocket server")
        }
    }

    private suspend fun WebSocketSession.handleIncomingFrames(ip : String) {
        for (frame in incoming) {
            when (frame) {
                is Frame.Text -> {
                    handleResponse(ip,frame.readText())
                }
                is Frame.Ping -> continue // Игнорируем ping-фреймы
                is Frame.Pong -> continue // Игнорируем pong-фреймы
                else -> Unit
            }
        }
    }

    private suspend fun handleResponse(ip: String, response: String){

        val message = cast(response)
        val type = message.type

        val client = _client.replayCache.firstOrNull { cacheItem ->
            cacheItem.ip == ip
        } ?: if(type == id) Client(ip = ip, id = message.text) else null

        if(client == null) return

        with(client){
            copy(messages = messages.apply { add(message) })
        }

        this._client.emit(client)
    }

    private suspend fun WebSocketSession.registerSession(ip: String){
        sessions[ip] = this
        sendCurrentTime(ip)
        send(ip, id.request())
    }



    private fun unregisterSession(ip: String) {
    }

    private fun handleError(exception: Throwable, context: String = "") {
        Log.e("WebSocketError", "$context: ${exception.message}")
    }

    fun stop() {
        try {
            server?.stop(1000, 3000)
            sessions.clear()
        } catch (e: Exception) {
            handleError(e, "Error while stopping server")
        }
    }

    suspend fun broadcastMessage(message: String) {
        sessions.forEach { (ip, session) ->
            try {
                session.send(Frame.Text(message))
            } catch (e: Exception) {
                handleError(e, "Failed to broadcast to $ip")
                sessions.remove(ip)
            }
        }
    }

    suspend fun WebSocketSession.sendCurrentTime(ip : String) {
        // Получаем текущий Unix timestamp (секунды с 1970)
        val unixTime = Instant.now().epochSecond

        // Формируем команду
        val timeCommand = "/settime $unixTime"

        // Отправляем команду
        send(ip, timeCommand)
        println("Sent to ESP32: $timeCommand")
    }

    override suspend fun send(ip: String, request: String) {
        sessions[ip]?.send(Frame.Text(request))
    }

}

sealed class ServerStatus {
    object Stopped : ServerStatus()
    object Running : ServerStatus()
    data class Error(val message: String) : ServerStatus()
}

enum class Action{
    custom,
    id,
    blink,
    setTime,
    time,
    error;

    fun request() = "/$name"
    fun response() = "$name:"
}

data class Massage(
    val value : String,
    val type: Action,
    val text : String
)

fun cast(response : String) : Massage{
    val action = Action.entries.firstOrNull { act ->
        act.name == response.substringBefore(":")
    } ?: Action.custom

    val text = response.substringAfter(":")
    return Massage(
        value = response,
        type = action,
        text = text
    )
}
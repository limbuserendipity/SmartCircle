import android.util.Log
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
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

class WebSocketServer(
    private val port: Int = 8080,
    private val pingInterval: Duration = Duration.ofSeconds(15),
    private val timeoutDuration: Duration = Duration.ofSeconds(30)
) {
    private var server: ApplicationEngine? = null
    private val sessions = ConcurrentHashMap<String, WebSocketSession>()

    // Callbacks для событий сервера
    var onServerStarted: (() -> Unit)? = null
    var onServerStopped: (() -> Unit)? = null
    var onClientConnected: ((ip: String) -> Unit)? = null
    var onClientDisconnected: ((ip: String) -> Unit)? = null
    var onMessageReceived: ((ip: String, message: String) -> Unit)? = null
    var onError: ((Throwable) -> Unit)? = null

    fun start() {
        try {
            server = embeddedServer(Netty, port = port) {
                install(WebSockets) {
                    pingPeriod = pingInterval
                    timeout = timeoutDuration
                }
                routing {
                    webSocket("/connect") {
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

            onServerStarted?.invoke()
        } catch (e: Exception) {
            handleError(e, "Failed to start WebSocket server")
        }
    }

    private suspend fun WebSocketSession.handleIncomingFrames(ip: String) {
        for (frame in incoming) {
            when (frame) {
                is Frame.Text -> {
                    val text = frame.readText()
                    onMessageReceived?.invoke(ip, text)
                }
                is Frame.Ping -> continue // Игнорируем ping-фреймы
                is Frame.Pong -> continue // Игнорируем pong-фреймы
                else -> Unit
            }
        }
    }

    private fun WebSocketSession.registerSession(ip: String) {
        sessions[ip] = this
        onClientConnected?.invoke(ip)
    }

    private fun unregisterSession(ip: String) {
        sessions.remove(ip)
        onClientDisconnected?.invoke(ip)
    }

    private fun handleError(exception: Throwable, context: String = "") {
        Log.e("WebSocketError", "$context: ${exception.message}")
        onError?.invoke(exception)
    }

    fun stop() {
        try {
            server?.stop(1000, 3000)
            sessions.clear()
            onServerStopped?.invoke()
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

    suspend fun sendToClient(ip: String, message: String): Boolean {
        return sessions[ip]?.let { session ->
            try {
                session.send(Frame.Text(message))
                true
            } catch (e: Exception) {
                handleError(e, "Failed to send message to $ip")
                sessions.remove(ip)
                false
            }
        } ?: false
    }

    fun getConnectedClients(): Set<String> = sessions.keys.toSet()
}

sealed class ServerStatus {
    object Stopped : ServerStatus()
    object Running : ServerStatus()
    data class Error(val message: String) : ServerStatus()
}
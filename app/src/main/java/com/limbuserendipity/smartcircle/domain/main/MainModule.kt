package com.limbuserendipity.smartcircle.domain.main

import com.limbuserendipity.smartcircle.data.core.WebSocketServer
import com.limbuserendipity.smartcircle.data.core.WebSocketServerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.SupervisorJob
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideWebSocketServer() : WebSocketServer = WebSocketServerImpl(
        port = 8080,
        pingInterval = Duration.ofSeconds(15),
        timeoutDuration = Duration.ofSeconds(30),
        job = SupervisorJob()
    )

}
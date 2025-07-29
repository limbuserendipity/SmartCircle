package com.limbuserendipity.smartcircle.presentation.home

import ServerStatus
import com.limbuserendipity.smartcircle.data.core.model.Arduino
import com.limbuserendipity.smartcircle.data.core.model.Client

data class HomeState(
    val clients : List<Client> = listOf(),
    val serverStatus : ServerStatus = ServerStatus.Stopped
)
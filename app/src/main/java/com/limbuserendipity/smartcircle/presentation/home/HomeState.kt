package com.limbuserendipity.smartcircle.presentation.home

import ServerStatus
import com.limbuserendipity.smartcircle.data.core.model.Arduino

data class HomeState(
    val arduinos : List<Arduino> = listOf(),
    val serverStatus : ServerStatus = ServerStatus.Stopped
)
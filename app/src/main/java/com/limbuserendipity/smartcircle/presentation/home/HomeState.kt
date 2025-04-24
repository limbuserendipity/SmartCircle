package com.limbuserendipity.smartcircle.presentation.home

import com.limbuserendipity.smartcircle.data.core.model.Arduino

data class HomeState(
    val arduinos : List<Arduino> = listOf()
)
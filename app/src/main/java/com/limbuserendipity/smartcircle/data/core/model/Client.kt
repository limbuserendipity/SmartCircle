package com.limbuserendipity.smartcircle.data.core.model

import com.limbuserendipity.smartcircle.data.core.Massage

data class Client(
    val ip : String,
    var id : String = "",
    val messages : MutableList<Massage> = mutableListOf()
)

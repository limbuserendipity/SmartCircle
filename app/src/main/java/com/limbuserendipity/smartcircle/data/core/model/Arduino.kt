package com.limbuserendipity.smartcircle.data.core.model

data class Arduino(
    val ip : String,
    var massage : String = "",
    val time : String = "",
    var status : ArduinoStatus = ArduinoStatus.Found,
)

enum class ArduinoStatus{
    Connected, Disabled, Found
}
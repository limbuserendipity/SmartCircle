package com.limbuserendipity.smartcircle.data.core.model

data class Arduino(
    val ip : String,
    val port : Int = 80,
    val massage : String = "",
    val status : ArduinoStatus = ArduinoStatus.Found
)

enum class ArduinoStatus{
    Connected, Disabled, Found
}
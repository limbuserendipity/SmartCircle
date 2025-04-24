package com.limbuserendipity.smartcircle.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.limbuserendipity.smartcircle.data.core.model.Arduino
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.jmdns.JmDNS
import javax.jmdns.ServiceListener
import javax.jmdns.ServiceEvent
import okhttp3.*
@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun update(newState: HomeState) {
        _state.update {
            newState
        }
    }

    // Создаем JmDNS
    fun searchIp() {
        viewModelScope.launch {
            Log.w("lolkek", "start search")
            withContext(Dispatchers.IO){
                val jmDNS: JmDNS = JmDNS.create()
                // Ищем все сервисы типа "_arduino._tcp.local."
                jmDNS.addServiceListener("anime._tcp.local.", object : ServiceListener {
                    override fun serviceAdded(event: ServiceEvent?) {
                        // Устройство найдено
                        val ip = event?.info?.hostAddresses?.first()
                        if (ip != null)
                            with(_state) {
                                val ars = value.arduinos.toMutableList()
                                ars.add(
                                    Arduino(
                                        ip = ip
                                    )
                                )
                                update(value.copy(arduinos = ars))
                            }

                        println("Найдено Arduino: $ip")
                        Log.w("lolkek", "Найдено Arduino: $ip")
                    }

                    override fun serviceRemoved(event: ServiceEvent?) {
                        TODO("Not yet implemented")
                    }

                    override fun serviceResolved(event: ServiceEvent?) {
                        Log.w("lolkek", "Найдено Arduino:")
                    }
                })
            }
        }
    }

}
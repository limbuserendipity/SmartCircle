package com.limbuserendipity.smartcircle.data.core

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo

class NsdHelper(private val nsdManager: NsdManager) {
    private val SERVICE_NAME = "MyWebSocketServer"
    private val SERVICE_TYPE = "_ws._tcp." // или "_http._tcp."
    private var registrationListener: NsdManager.RegistrationListener? = null

    fun registerService(port: Int) {
        val serviceInfo = NsdServiceInfo().apply {
            serviceName = SERVICE_NAME
            serviceType = SERVICE_TYPE
            setPort(port)
        }

        registrationListener = object : NsdManager.RegistrationListener {
            override fun onServiceRegistered(serviceInfo: NsdServiceInfo) {
                println("Service registered: ${serviceInfo.serviceName}")
            }
            override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                println("Registration failed: $errorCode")
            }
            override fun onServiceUnregistered(serviceInfo: NsdServiceInfo) {
                println("Service unregistered")
            }
            override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                println("Unregistration failed: $errorCode")
            }
        }

        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
    }

    fun unregisterService() {
        registrationListener?.let { nsdManager.unregisterService(it) }
    }
}
package com.limbuserendipity.smartcircle.presentation.main

import android.content.Context
import android.net.nsd.NsdManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.limbuserendipity.smartcircle.data.core.NsdHelper
import dagger.hilt.android.AndroidEntryPoint
import com.limbuserendipity.smartcircle.presentation.navigator.AppNavHost
import com.limbuserendipity.smartcircle.presentation.navigator.AppNavigator
import com.limbuserendipity.smartcircle.presentation.theme.SmartCircleTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current
            val nsdHelper = remember {
                NsdHelper(context.getSystemService(NSD_SERVICE) as NsdManager)
            }

            SmartCircleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController: NavHostController = rememberNavController()
                    appNavigator.setController(navController)
                    AppNavHost(
                        navController,
                        appNavigator.startScreen()
                    )
                }
            }

            LaunchedEffect(Unit) {
                nsdHelper.registerService(8080) // или nsdHelper.discoverServices()
            }

        }
    }
}

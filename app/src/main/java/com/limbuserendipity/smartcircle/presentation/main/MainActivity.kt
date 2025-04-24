package com.limbuserendipity.smartcircle.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
        }
    }
}

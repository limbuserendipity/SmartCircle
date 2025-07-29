package com.limbuserendipity.smartcircle.presentation.navigator

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.limbuserendipity.smartcircle.presentation.home.HomeScreen
import com.limbuserendipity.smartcircle.presentation.client_details.ClientDetailsScreen
import com.limbuserendipity.smartcircle.presentation.client_details.ClientDetailsViewModel
import com.limbuserendipity.smartcircle.presentation.langs.ProgrammingLanguagesScreen
import com.limbuserendipity.smartcircle.presentation.langs.ProgrammingLanguagesViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    startScreen: Screen
) {
    NavHost(
        navController = navController,
        startDestination = startScreen
    ) {
        composable<Screen.HomeScreen>(){
            HomeScreen(hiltViewModel())
        }

        composable<Screen.ProgrammingLanguages> {
            ProgrammingLanguagesScreen(hiltViewModel<ProgrammingLanguagesViewModel>())
        }

        composable<Screen.ClientDetails> {
            ClientDetailsScreen(hiltViewModel<ClientDetailsViewModel>())
        }
    }
}

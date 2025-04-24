package com.limbuserendipity.smartcircle.presentation.navigator

import androidx.navigation.NavHostController
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor() : AppNavigator {
    private var navHostController: NavHostController? = null

    override fun setController(controller: NavHostController) {
        navHostController = controller
    }

    override fun startScreen(): Screen = Screen.HomeScreen

    override fun navigateTo(screen: Screen) {
        navHostController?.navigate(screen)
    }

    override fun navigateBack(): Boolean {
        navHostController?.run {
            return popBackStack()
        }
        return false
    }
}
package com.limbuserendipity.smartcircle.presentation.langs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.limbuserendipity.smartcircle.domain.langs.ProgrammingLanguagesManager
import com.limbuserendipity.smartcircle.presentation.navigator.AppNavigator
import com.limbuserendipity.smartcircle.presentation.navigator.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgrammingLanguagesViewModel @Inject constructor(
    private val programmingLanguagesManager: ProgrammingLanguagesManager,
    private val appNavigator: AppNavigator
) :
    ViewModel() {

    private val programmingLanguagesViewStateMutable =
        MutableStateFlow(getInitProgrammingLanguagesViewState())
    val programmingLanguagesViewState = programmingLanguagesViewStateMutable.asStateFlow()

    init {
        getProgrammingLanguages()
    }

    fun detailsClicked(name: String) {
        appNavigator.navigateTo(Screen.ClientDetails(name))
    }

    private fun getProgrammingLanguages() {
        viewModelScope.launch(Dispatchers.IO) {
            val programmingLanguages = programmingLanguagesManager.getProgrammingLanguages()
            programmingLanguagesViewStateMutable.update { currentState ->
                currentState.copy(
                    programmingLanguages = programmingLanguages,
                    isLoading = false
                )
            }
        }
    }

    private fun getInitProgrammingLanguagesViewState() = ProgrammingLanguagesViewState(
        programmingLanguages = emptyList(),
        isLoading = true
    )
}
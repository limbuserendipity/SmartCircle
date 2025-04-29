package com.limbuserendipity.smartcircle.presentation.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel@Inject constructor(

) : ViewModel(){
    val _state = MutableStateFlow(SearchState())

    val state = _state.asStateFlow()

    fun bruteForceSearch() {

    }

}

data class SearchState(
    val ips : List<String> = listOf<String>()
)

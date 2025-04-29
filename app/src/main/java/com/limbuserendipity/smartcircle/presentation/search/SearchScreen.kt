package com.limbuserendipity.smartcircle.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel

@Composable
fun SearchScreen(viewModel : SearchViewModel){

    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.bruteForceSearch()
    }

}

@Composable
fun SearchContent(){



}
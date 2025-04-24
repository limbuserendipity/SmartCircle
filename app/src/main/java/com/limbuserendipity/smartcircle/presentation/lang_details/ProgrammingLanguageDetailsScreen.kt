package com.limbuserendipity.smartcircle.presentation.lang_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import limbuserendipity.smartcircle.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgrammingLanguageDetailsScreen(programmingLanguageDetailsViewModel: ProgrammingLanguageDetailsViewModel) {

    val programmingLanguageDetailsViewState =
        programmingLanguageDetailsViewModel.programmingLanguageDetailsViewState.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(programmingLanguageDetailsViewState.langName)
                },
                navigationIcon = {
                    IconButton(onClick = { programmingLanguageDetailsViewModel.backClicked() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }) { innerPadding ->
        Content(
            programmingLanguageDetailsViewState,
            innerPadding
        )
    }
}

@Composable
private fun Content(
    programmingLanguageDetailsViewState: ProgrammingLanguageDetailsViewState,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        if (programmingLanguageDetailsViewState.isLoading) {
            LoadingContent()
        } else {
            LoadedContent(programmingLanguageDetailsViewState.programmingLanguageDetails.description)
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun LoadedContent(description: String = stringResource(R.string.app_name)) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(description)
    }
}


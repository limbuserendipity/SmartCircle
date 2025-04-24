package com.limbuserendipity.smartcircle.presentation.lang_details

import limbuserendipity.smartcircle.data.core.model.ProgrammingLanguageDetails

data class ProgrammingLanguageDetailsViewState(
    val langName: String,
    val programmingLanguageDetails: ProgrammingLanguageDetails,
    val isLoading: Boolean
)

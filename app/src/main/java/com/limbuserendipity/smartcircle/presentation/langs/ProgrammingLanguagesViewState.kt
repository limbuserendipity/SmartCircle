package com.limbuserendipity.smartcircle.presentation.langs

import limbuserendipity.smartcircle.data.core.model.ProgrammingLanguage

data class ProgrammingLanguagesViewState(
    val programmingLanguages: List<ProgrammingLanguage>,
    val isLoading: Boolean
)

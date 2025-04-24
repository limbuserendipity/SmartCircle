package com.limbuserendipity.smartcircle.data.core

import limbuserendipity.smartcircle.data.core.model.ProgrammingLanguage
import limbuserendipity.smartcircle.data.core.model.ProgrammingLanguageDetails

interface Api {
    suspend fun getProgrammingLanguages(): List<ProgrammingLanguage>
    suspend fun getProgrammingLanguageDetails(name: String): ProgrammingLanguageDetails
}
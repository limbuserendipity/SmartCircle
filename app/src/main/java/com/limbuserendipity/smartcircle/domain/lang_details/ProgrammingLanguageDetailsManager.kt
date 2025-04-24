package com.limbuserendipity.smartcircle.domain.lang_details

import limbuserendipity.smartcircle.data.core.model.ProgrammingLanguageDetails

interface ProgrammingLanguageDetailsManager {
    suspend fun getProgrammingLanguageDetails(name: String): ProgrammingLanguageDetails
}
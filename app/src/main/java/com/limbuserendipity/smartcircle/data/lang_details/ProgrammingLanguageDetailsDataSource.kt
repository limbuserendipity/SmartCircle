package com.limbuserendipity.smartcircle.data.lang_details

import limbuserendipity.smartcircle.data.core.model.ProgrammingLanguageDetails

interface ProgrammingLanguageDetailsDataSource {
    suspend fun getProgrammingLanguageDetails(name: String): ProgrammingLanguageDetails
}
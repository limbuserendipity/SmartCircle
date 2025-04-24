package com.limbuserendipity.smartcircle.domain.lang_details

import limbuserendipity.smartcircle.data.core.model.ProgrammingLanguageDetails
import com.limbuserendipity.smartcircle.data.lang_details.ProgrammingLanguageDetailsDataSource
import javax.inject.Inject

class ProgrammingLanguageDetailsManagerImpl @Inject constructor(private val programmingLanguageDetailsDataSource: ProgrammingLanguageDetailsDataSource) :
    ProgrammingLanguageDetailsManager {

    override suspend fun getProgrammingLanguageDetails(name: String): ProgrammingLanguageDetails =
        programmingLanguageDetailsDataSource.getProgrammingLanguageDetails(name)

}
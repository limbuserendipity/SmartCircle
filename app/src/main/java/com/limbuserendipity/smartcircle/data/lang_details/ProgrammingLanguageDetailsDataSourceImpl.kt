package com.limbuserendipity.smartcircle.data.lang_details

import com.limbuserendipity.smartcircle.data.core.Api
import com.limbuserendipity.smartcircle.data.lang_details.ProgrammingLanguageDetailsDataSource
import limbuserendipity.smartcircle.data.core.model.ProgrammingLanguageDetails
import javax.inject.Inject

class ProgrammingLanguageDetailsDataSourceImpl @Inject constructor(private val api: Api) :
    ProgrammingLanguageDetailsDataSource {

    override suspend fun getProgrammingLanguageDetails(name: String): ProgrammingLanguageDetails =
        api.getProgrammingLanguageDetails(name)
}
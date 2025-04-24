package com.limbuserendipity.smartcircle.data.langs

import com.limbuserendipity.smartcircle.data.core.Api
import com.limbuserendipity.smartcircle.data.langs.ProgrammingLanguagesDataSource
import limbuserendipity.smartcircle.data.core.model.ProgrammingLanguage
import javax.inject.Inject

class ProgrammingLanguagesDataSourceImpl @Inject constructor(private val api: Api) :
    ProgrammingLanguagesDataSource {

    override suspend fun getProgrammingLanguages(): List<ProgrammingLanguage> =
        api.getProgrammingLanguages()
}
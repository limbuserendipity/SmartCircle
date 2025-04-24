package com.limbuserendipity.smartcircle.domain.langs

import limbuserendipity.smartcircle.data.core.model.ProgrammingLanguage
import com.limbuserendipity.smartcircle.data.langs.ProgrammingLanguagesDataSource
import javax.inject.Inject

class ProgrammingLanguagesManagerImpl @Inject constructor(private val programmingLanguagesDataSource: ProgrammingLanguagesDataSource) :
    ProgrammingLanguagesManager {

    override suspend fun getProgrammingLanguages(): List<ProgrammingLanguage> =
        programmingLanguagesDataSource.getProgrammingLanguages()
}
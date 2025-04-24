package com.limbuserendipity.smartcircle.data.langs

import limbuserendipity.smartcircle.data.core.model.ProgrammingLanguage

interface ProgrammingLanguagesDataSource {
    suspend fun getProgrammingLanguages(): List<ProgrammingLanguage>
}
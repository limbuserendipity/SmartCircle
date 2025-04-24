package com.limbuserendipity.smartcircle.domain.langs

import limbuserendipity.smartcircle.data.core.model.ProgrammingLanguage

interface ProgrammingLanguagesManager {
    suspend fun getProgrammingLanguages(): List<ProgrammingLanguage>
}
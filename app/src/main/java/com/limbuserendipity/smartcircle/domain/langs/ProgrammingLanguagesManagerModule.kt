package com.limbuserendipity.smartcircle.domain.langs

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.limbuserendipity.smartcircle.data.langs.ProgrammingLanguagesDataSource

@Module
@InstallIn(SingletonComponent::class)
object ProgrammingLanguagesManagerModule {

    @Provides
    fun provideProgrammingLanguagesManager(programmingLanguagesDataSource: ProgrammingLanguagesDataSource): ProgrammingLanguagesManager =
        ProgrammingLanguagesManagerImpl(programmingLanguagesDataSource)
}
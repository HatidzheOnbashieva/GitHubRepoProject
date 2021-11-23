package com.hatidzheonbashieva.githubrepoproject.di

import com.hatidzheonbashieva.githubrepoproject.database.RepoDao
import com.hatidzheonbashieva.githubrepoproject.searchRepository.StarredRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StarredRepositoryModule {
    @Singleton
    @Provides
    fun provideStarredRepository(repoDao: RepoDao): StarredRepository{
        return StarredRepository(repoDao)
    }
}
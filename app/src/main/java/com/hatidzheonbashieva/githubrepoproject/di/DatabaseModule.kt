package com.hatidzheonbashieva.githubrepoproject.di

import android.content.Context
import androidx.room.Room
import com.hatidzheonbashieva.githubrepoproject.database.RepoDao
import com.hatidzheonbashieva.githubrepoproject.database.RepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRepoDao(repoDatabase: RepoDatabase): RepoDao{
        return repoDatabase.repoDao()
    }

    @Provides
    @Singleton
    fun provideRepoDatabase(@ApplicationContext appContext: Context): RepoDatabase{
        return Room.databaseBuilder(
            appContext,
            RepoDatabase::class.java,
            "repositories.db"
        ).build()
    }
}
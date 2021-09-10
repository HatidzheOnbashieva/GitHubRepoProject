package com.hatidzheonbashieva.githubrepoproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RepoEntity::class], version = 1)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    companion object {

        private var instance: RepoDatabase? = null

        @Synchronized
        fun getRepoDatabase(context: Context): RepoDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RepoDatabase::class.java,
                    "repositories.db"
                ).build()

            return instance as RepoDatabase
        }
    }
}
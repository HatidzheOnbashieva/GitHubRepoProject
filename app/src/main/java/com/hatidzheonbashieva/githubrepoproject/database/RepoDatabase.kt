package com.hatidzheonbashieva.githubrepoproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RepoEntity::class], version = 1)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    companion object {
        @Volatile
        private var INSTANCE: RepoDatabase? = null

        fun getRepoDatabase(context: Context): RepoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) { //everything inside the block will be protected from a concurrent execution of multiple threads?
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    RepoDatabase::class.java,
                    "repositories.db"
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}
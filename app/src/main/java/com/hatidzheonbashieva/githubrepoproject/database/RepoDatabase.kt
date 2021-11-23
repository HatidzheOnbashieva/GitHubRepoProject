package com.hatidzheonbashieva.githubrepoproject.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hatidzheonbashieva.githubrepoproject.App


@Database(entities = [RepoEntity::class], version = 1)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

}

//object SingleRepoDatabase{
//    val instance: RepoDatabase by lazy {
//        Room.databaseBuilder(
//            App.appContext,
//            RepoDatabase::class.java,
//            "repositories.db"
//        ).build()
//    }
//}
package com.hatidzheonbashieva.githubrepoproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    @Query("SELECT * FROM repository ORDER BY username ASC")
    fun getRepos(): Flow<List<RepoEntity>>

    @Query("DELETE FROM repository WHERE repoId = :repoId")
    suspend fun deleteRepo(repoId: Int)

    @Insert
    suspend fun addRepo(repos: RepoEntity)

    @Query("SELECT EXISTS(SELECT * FROM repository WHERE repoId = :repoId)")
    suspend fun getRepoId(repoId: Int): Boolean
}
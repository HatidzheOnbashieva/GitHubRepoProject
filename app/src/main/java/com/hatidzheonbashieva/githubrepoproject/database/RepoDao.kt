package com.hatidzheonbashieva.githubrepoproject.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hatidzheonbashieva.githubrepoproject.model.Repos

@Dao
interface RepoDao {

    @Query("SELECT * FROM repository ORDER BY username ASC")
    fun getRepos(): LiveData<List<Repos>>

    @Query("DELETE FROM repository WHERE repoId = :repoId")
    fun deleteRepo(repoId: Int)

    @Insert
    suspend fun addRepo(repos: Repos)
}
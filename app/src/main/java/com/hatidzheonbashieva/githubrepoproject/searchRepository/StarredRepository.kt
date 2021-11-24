package com.hatidzheonbashieva.githubrepoproject.searchRepository

import androidx.lifecycle.LiveData
import com.hatidzheonbashieva.githubrepoproject.database.RepoDao
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import javax.inject.Inject

class StarredRepository @Inject constructor(private val repoDao: RepoDao) {
    //private var repoDao: RepoDao = SingleRepoDatabase.instance.repoDao()

    fun getAllRepos(): LiveData<List<RepoEntity>> = repoDao.getRepos()

    fun getRepoId(repoID: Int): LiveData<Boolean> = repoDao.getRepoId(repoID)

    suspend fun addRepo(repo: RepoEntity) {
        repoDao.addRepo(repo)
    }

    suspend fun deleteRepo(repoId: Int) {
        repoDao.deleteRepo(repoId)
    }
}

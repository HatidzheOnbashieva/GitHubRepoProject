package com.hatidzheonbashieva.githubrepoproject.searchRepository

import android.app.Application
import androidx.lifecycle.LiveData
import com.hatidzheonbashieva.githubrepoproject.database.RepoDao
import com.hatidzheonbashieva.githubrepoproject.database.RepoDatabase
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity

object StarredRepository {

    class RepoRepository(application: Application) {

        private var repoDao: RepoDao
        private var readAllRepos: LiveData<List<RepoEntity>>

        private var database = RepoDatabase.getRepoDatabase(application)

        init {
            repoDao = database.repoDao()
            readAllRepos = repoDao.getRepos()
        }

        fun getAllRepos(): LiveData<List<RepoEntity>> = readAllRepos

        fun getRepoId(repoID: Int): LiveData<Boolean> = repoDao.getRepoId(repoID)


        suspend fun addRepo(repo: RepoEntity) {
            repoDao.addRepo(repo)
        }

        suspend fun deleteRepo(repoId: Int) {
            repoDao.deleteRepo(repoId)
        }
    }
}
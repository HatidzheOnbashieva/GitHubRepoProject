package com.hatidzheonbashieva.githubrepoproject.searchRepository

import androidx.lifecycle.LiveData
import com.hatidzheonbashieva.githubrepoproject.database.RepoDao
import com.hatidzheonbashieva.githubrepoproject.model.Repos

object StarredRepository {

    class RepoRepository(private val repoDao: RepoDao){

//        val readRepos: LiveData<List<Repos>> = repoDao.getRepos()

        fun getRepos() :LiveData<List<Repos>>{
            return repoDao.getRepos()
        }

        suspend fun addRepo(repo: Repos){
            repoDao.addRepo(repo)
        }
    }
}
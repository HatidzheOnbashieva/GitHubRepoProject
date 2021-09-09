package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.hatidzheonbashieva.githubrepoproject.database.RepoDatabase
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import com.hatidzheonbashieva.githubrepoproject.searchRepository.StarredRepository

class StarredViewModel(application: Application) : AndroidViewModel(application) {

    val readAllRepos: LiveData<List<Repos>>
    private val repository: StarredRepository.RepoRepository

    init{
        val repoDao = RepoDatabase.getRepoDatabase(application).repoDao()
        repository = StarredRepository.RepoRepository(repoDao)
        readAllRepos = repository.getRepos()
    }

}

package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.searchRepository.StarredRepository

class StarredViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StarredRepository.RepoRepository(application)
    private val readAllRepos = repository.getAllRepos()

    fun getAllRepos(): LiveData<List<RepoEntity>> = readAllRepos
}

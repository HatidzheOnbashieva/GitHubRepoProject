package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.searchRepository.StarredRepository

class StarredViewModel : ViewModel() {

    fun getAllRepos(): LiveData<List<RepoEntity>> = StarredRepository.getAllRepos()
}

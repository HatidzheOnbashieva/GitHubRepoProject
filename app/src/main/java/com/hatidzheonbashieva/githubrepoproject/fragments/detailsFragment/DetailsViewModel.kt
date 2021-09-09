package com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatidzheonbashieva.githubrepoproject.database.RepoDatabase
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import com.hatidzheonbashieva.githubrepoproject.searchRepository.StarredRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {


    private lateinit var repository: StarredRepository.RepoRepository

    fun init(application: Application){
        val repoDao = RepoDatabase.getRepoDatabase(application).repoDao()
        repository = StarredRepository.RepoRepository(repoDao)
    }

    fun addRepo(repo: Repos) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addRepo(repo)
        }
    }
}
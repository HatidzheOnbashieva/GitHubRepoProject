package com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.searchRepository.StarredRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StarredRepository.RepoRepository(application)

    fun addRepo(repo: RepoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRepo(repo)
        }
    }

    fun getRepoId(repoId: Int): LiveData<Boolean> {
        val response = repository.getRepoId(repoId)
        return response
    }

    fun deleteRepoId(repoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRepo(repoId)
        }
    }
}
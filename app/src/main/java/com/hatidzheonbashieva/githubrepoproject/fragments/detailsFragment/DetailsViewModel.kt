package com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.searchRepository.StarredRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    fun addRepo(repo: RepoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            StarredRepository.addRepo(repo)
        }
    }

    fun getRepoId(repoId: Int): LiveData<Boolean> = StarredRepository.getRepoId(repoId)


    fun deleteRepoId(repoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            StarredRepository.deleteRepo(repoId)
        }
    }
}
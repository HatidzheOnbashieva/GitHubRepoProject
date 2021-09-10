package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import androidx.lifecycle.*
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import com.hatidzheonbashieva.githubrepoproject.searchRepository.SearchRepository

class SearchViewModel : ViewModel() {

    private var _username: MutableLiveData<String> = MutableLiveData()

    val userRepoList: LiveData<List<Repos>> = Transformations.switchMap(_username) {
        SearchRepository.getUserRepos(it)
    }

    fun setUsername(username: String) {
        _username.value = username
    }

    fun getUsername() = _username.value

    fun cancelJobs() {
        SearchRepository.cancelJobs()
    }

}
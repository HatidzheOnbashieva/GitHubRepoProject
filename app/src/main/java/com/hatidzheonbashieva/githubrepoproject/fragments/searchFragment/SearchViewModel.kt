package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import com.hatidzheonbashieva.githubrepoproject.searchRepository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    val userRepos: MutableLiveData<List<Repos>> = MutableLiveData()

    fun setUsername(username: String){
        viewModelScope.launch(Dispatchers.IO) {
            userRepos.postValue(SearchRepository.getUserRepos(username))
        }
    }
}
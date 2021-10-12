package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import com.hatidzheonbashieva.githubrepoproject.searchRepository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SearchViewModel : ViewModel() {

    val userRepos: MutableLiveData<List<Repos>> = MutableLiveData()

    fun setUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepos.postValue(SearchRepository.getUserRepos(username))
            } catch (ex: HttpException) {
                when (ex.code()) {
                    404 -> userRepos.postValue(emptyList())
                    500 -> userRepos.postValue(emptyList())
                    else -> userRepos.postValue(emptyList())
                }
            }
        }
    }
}
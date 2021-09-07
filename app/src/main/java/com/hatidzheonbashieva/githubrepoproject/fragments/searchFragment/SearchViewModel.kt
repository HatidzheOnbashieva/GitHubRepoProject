package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import com.hatidzheonbashieva.githubrepoproject.searchRepository.SearchRepository

class SearchViewModel : ViewModel() {

    private var _username: MutableLiveData<String> = MutableLiveData()

    val userRepoList: LiveData<List<Repos>> = Transformations.switchMap(_username){
        SearchRepository.getUserRepos(it)
    }

    fun setUsername(username: String){
        val update = username
        if(_username.value == update){
            return
        }
        _username.value = username
    }

    fun cancelJobs(){
        SearchRepository.cancelJobs()
    }

}
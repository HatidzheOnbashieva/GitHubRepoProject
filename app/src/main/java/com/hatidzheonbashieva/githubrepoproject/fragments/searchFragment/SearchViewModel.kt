package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import com.hatidzheonbashieva.githubrepoproject.searchRepository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
    ) : ViewModel() {

    val userRepos: MutableLiveData<List<Repos>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun setUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepos.postValue(searchRepository.getUserRepos(username))
            }catch (ex: HttpException) {
                when (ex.code()) {
                    301 -> {
                        userRepos.postValue(emptyList())
                        errorMessage.postValue("Moved Permanently")
                    }
                    403 -> {
                        userRepos.postValue(emptyList())
                        errorMessage.postValue("Forbidden - API rate limit exceeded")
                    }
                    404 -> {
                        userRepos.postValue(emptyList())
                        errorMessage.postValue("Not Found")
                    }
                    500 -> {
                        userRepos.postValue(emptyList())
                        errorMessage.postValue("Internal Server Error")
                    }
                    else -> {
                        userRepos.postValue(emptyList())
                        errorMessage.postValue("Unknown Error")
                    }
                }
            }
        }
    }
}
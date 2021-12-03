package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import com.hatidzheonbashieva.githubrepoproject.searchRepository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
    ) : ViewModel() {

    val userRepos: MutableSharedFlow<List<Repos>> = MutableSharedFlow()
    val errorMessage: MutableSharedFlow<String> = MutableSharedFlow()

    fun setUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepos.emit(searchRepository.getUserRepos(username))
            }catch (ex: HttpException) {
                when (ex.code()) {
                    301 -> {
                        userRepos.emit(emptyList())
                        errorMessage.emit("Moved Permanently")
                    }
                    403 -> {
                        userRepos.emit(emptyList())
                        errorMessage.emit("Forbidden - API rate limit exceeded")
                    }
                    404 -> {
                        userRepos.emit(emptyList())
                        errorMessage.emit("Not Found")
                    }
                    500 -> {
                        userRepos.emit(emptyList())
                        errorMessage.emit("Internal Server Error")
                    }
                    else -> {
                        userRepos.emit(emptyList())
                        errorMessage.emit("Unknown Error")
                    }
                }
            }
        }
    }
}
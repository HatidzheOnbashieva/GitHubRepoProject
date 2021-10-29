package com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment

import androidx.lifecycle.*
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import com.hatidzheonbashieva.githubrepoproject.searchRepository.SearchRepository
import com.hatidzheonbashieva.githubrepoproject.searchRepository.StarredRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailsViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    //val arguments: MutableLiveData<RepoDetailsArgument> = MutableLiveData()

    val arguments: MutableLiveData<Repos> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
       val savedArguments = savedStateHandle.get<RepoDetailsArgument>("argument")
        //api call here
        setRepos(savedArguments!!.userName, savedArguments.repoName)
        //arguments.postValue(savedArguments)
    }

    private fun setRepos(userName: String, repoName: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                arguments.postValue(SearchRepository.getRepos(userName, repoName))

            } catch (ex: HttpException) {
                when (ex.code()) {
                    301 -> {
                        arguments.postValue(Repos())
                        errorMessage.postValue("Moved Permanently")
                    }
                    403 -> {
                        arguments.postValue(Repos())
                        errorMessage.postValue("Forbidden - API rate limit exceeded")
                    }
                    404 -> {
                        arguments.postValue(Repos())
                        errorMessage.postValue("Not Found")
                    }
                    500 -> {
                        arguments.postValue(Repos())
                        errorMessage.postValue("Internal Server Error")
                    }
                    else -> {
                        arguments.postValue(Repos())
                        errorMessage.postValue("Unknown Error")
                    }
                }
            }
        }
    }

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
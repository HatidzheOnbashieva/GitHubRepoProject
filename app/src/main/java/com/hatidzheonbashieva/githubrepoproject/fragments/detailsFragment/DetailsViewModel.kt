package com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import com.hatidzheonbashieva.githubrepoproject.searchRepository.SearchRepository
import com.hatidzheonbashieva.githubrepoproject.searchRepository.StarredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val starredRepository: StarredRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //val arguments: MutableLiveData<RepoDetailsArgument> = MutableLiveData()

    val arguments: MutableSharedFlow<Repos> = MutableSharedFlow()
    val errorMessage: MutableSharedFlow<String> = MutableSharedFlow()

    init {
        val savedArguments = savedStateHandle.get<RepoDetailsArgument>("argument")
        //api call here
        setRepos(savedArguments!!.userName, savedArguments.repoName)
        //arguments.postValue(savedArguments)
    }

    private fun setRepos(userName: String, repoName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                arguments.emit(searchRepository.getRepos(userName, repoName))

            } catch (ex: HttpException) {
                when (ex.code()) {
                    301 -> {
                        errorMessage.emit("Moved Permanently")
                    }
                    403 -> {
                        errorMessage.emit("Forbidden - API rate limit exceeded")
                    }
                    404 -> {
                        errorMessage.emit("Not Found")
                    }
                    500 -> {
                        errorMessage.emit("Internal Server Error")
                    }
                    else -> {
                        errorMessage.emit("Unknown Error")
                    }
                }
            }
        }
    }

    fun addRepo(repo: RepoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            starredRepository.addRepo(repo)
        }
    }

    suspend fun getRepoId(repoId: Int) =  starredRepository.getRepoId(repoId)

    fun deleteRepoId(repoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            starredRepository.deleteRepo(repoId)
        }
    }


//     fun execute(callback: suspend CoroutineScope.() -> Unit){
//        viewModelScope.launch(Dispatchers.IO) {
//            this.callback()
//        }
//    }


}
package com.hatidzheonbashieva.githubrepoproject.searchRepository

import androidx.lifecycle.LiveData
import com.hatidzheonbashieva.githubrepoproject.api.RetrofitBuilder
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.HttpException

object SearchRepository {

    //private var USERNAME = "hatidzheonbashieva"
    private var TYPE = "owner"
    private var PAGE = 1
    private var PER_PAGE = 50
    private var SORT = "name"
    private var getUserRepos: List<Repos>? = emptyList()

    var job: CompletableJob? = null
    fun getUserRepos(username: String): LiveData<List<Repos>>{
        job = Job()
        return object:LiveData<List<Repos>>(){
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->

                    CoroutineScope(IO + theJob).launch {
                        try {
                                getUserRepos = RetrofitBuilder.apiService.getUserRepos(
                                username,
                                TYPE,
                                PER_PAGE,
                                PAGE,
                                SORT
                            )
                        } catch (ex: HttpException) {
                            getUserRepos = emptyList()
                            println("Error")
                        }
                        withContext(Main) {
                            value = getUserRepos
                            theJob.complete()
                        }
                    }
                }
            }
        }
    }
    fun cancelJobs(){
        job?.cancel()
    }
}
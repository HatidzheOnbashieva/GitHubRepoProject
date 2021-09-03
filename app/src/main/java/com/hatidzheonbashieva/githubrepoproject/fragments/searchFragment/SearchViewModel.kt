package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hatidzheonbashieva.githubrepoproject.model.Repo
import com.hatidzheonbashieva.githubrepoproject.online.API
import com.hatidzheonbashieva.githubrepoproject.online.ServiceProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    var gitHubApi: API = ServiceProvider.RetrofitInstance().getRetrofitInstance().create(API::class.java)
    var repoList: MutableLiveData<List<Repo>> = MutableLiveData()
    private var getUsername: String? = null

    //var repoList: List<Repo>? = mutableListOf()


    private var USERNAME = "hatidzheonbashieva"
    private var TYPE = "owner"
    private var PAGE = 1
    private var PER_PAGE = 50
    private var SORT = "name"

    fun fetchData() : LiveData<List<Repo>> {
        val call: Call<List<Repo>>? = gitHubApi.getUserRepos(getUsername, TYPE, PER_PAGE, PAGE, SORT)
        call?.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
               repoList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                t.message
            }

        })
        return repoList
    }

    fun setUsername(sentUsername: String?): String? {
        getUsername = sentUsername
        return getUsername

    }



}
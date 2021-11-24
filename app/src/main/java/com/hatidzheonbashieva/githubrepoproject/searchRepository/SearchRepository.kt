package com.hatidzheonbashieva.githubrepoproject.searchRepository

import com.hatidzheonbashieva.githubrepoproject.api.ApiService
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: ApiService) {

    private var TYPE = "owner"
    private var PAGE = 1
    private var PER_PAGE = 50
    private var SORT = "name"


    suspend fun getUserRepos(username: String): List<Repos> {
        return apiService.getUserRepos(
            username,
            TYPE,
            PER_PAGE,
            PAGE,
            SORT
        )
    }

    suspend fun getRepos(username: String, repoName: String): Repos {
        return apiService.getRepos(
            username,
            repoName
        )
    }
}
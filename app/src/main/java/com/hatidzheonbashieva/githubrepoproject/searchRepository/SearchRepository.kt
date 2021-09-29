package com.hatidzheonbashieva.githubrepoproject.searchRepository

import com.hatidzheonbashieva.githubrepoproject.api.RetrofitBuilder
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import retrofit2.HttpException

object SearchRepository {

    private var TYPE = "owner"
    private var PAGE = 1
    private var PER_PAGE = 50
    private var SORT = "name"

    suspend fun getUserRepos(username: String): List<Repos> {
        val userRepos = try {
            RetrofitBuilder.apiService.getUserRepos(
                    username,
                    TYPE,
                    PER_PAGE,
                    PAGE,
                    SORT
            )

        } catch (ex: HttpException) {
            emptyList()
        }
        return userRepos
    }

    suspend fun getRepos(username: String, repoName: String): Repos {
        return RetrofitBuilder.apiService.getRepos(
                    username,
                    repoName
            )
    }
}
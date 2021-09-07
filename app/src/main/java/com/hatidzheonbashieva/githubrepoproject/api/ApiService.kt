package com.hatidzheonbashieva.githubrepoproject.api

import com.hatidzheonbashieva.githubrepoproject.model.Repos
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String?,
        @Query("type") type: String?,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
        @Query("sort") sort: String?
    ): List<Repos>
}
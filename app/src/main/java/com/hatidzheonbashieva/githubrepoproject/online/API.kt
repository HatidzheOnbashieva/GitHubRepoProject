package com.hatidzheonbashieva.githubrepoproject.online

import com.hatidzheonbashieva.githubrepoproject.model.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("users/{username}/repos")
    fun getUserRepos(
        @Path("username") username: String?,
        @Query("type") type: String?,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
        @Query("sort") sort: String?
    ): Call<List<Repo>>
}
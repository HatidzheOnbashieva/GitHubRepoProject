package com.hatidzheonbashieva.githubrepoproject.online

import com.hatidzheonbashieva.githubrepoproject.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("users/{username}/repos")
    fun getMovies(
        @Path("username") username: String?,
        @Query("type") type: String?,
        @Query("per-page") per_page: Int,
        @Query("page") page: Int
    ): Call<Users>?
}
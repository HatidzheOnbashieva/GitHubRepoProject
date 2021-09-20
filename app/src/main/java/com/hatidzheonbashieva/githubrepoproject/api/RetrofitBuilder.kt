package com.hatidzheonbashieva.githubrepoproject.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://api.github.com/"

    //by lazy means that you have to only initialize this variable once and when it is called for the first time
    private val retrofitInstance: Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    //we need the GsonConverter library to convert Json data to Java objects
    // we are able to use this library in Kotlin because Kotlin compiles down to Java
    }

    val apiService: ApiService by lazy {
        retrofitInstance.build().create(ApiService::class.java)
    }

}
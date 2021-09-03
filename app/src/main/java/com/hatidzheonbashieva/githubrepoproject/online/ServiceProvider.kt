package com.hatidzheonbashieva.githubrepoproject.online

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceProvider {

    class RetrofitInstance {

        val BASE_URL = "https://api.github.com/"

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

    }

}
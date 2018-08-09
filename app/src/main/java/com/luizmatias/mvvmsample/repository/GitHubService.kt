package com.luizmatias.mvvmsample.repository

import com.luizmatias.mvvmsample.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubService {

    companion object {
        fun create(): GitHubService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()
            return retrofit.create(GitHubService::class.java)
        }
    }

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Call<User>


}
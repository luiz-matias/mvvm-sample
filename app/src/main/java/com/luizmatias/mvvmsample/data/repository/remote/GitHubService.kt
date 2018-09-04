package com.luizmatias.mvvmsample.data.repository.remote

import com.luizmatias.mvvmsample.data.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubService {

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Observable<User>

}
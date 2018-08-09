package com.luizmatias.mvvmsample.repository

import android.arch.lifecycle.MutableLiveData
import com.luizmatias.mvvmsample.model.User
import com.luizmatias.mvvmsample.model.UserHandler
import com.luizmatias.mvvmsample.viewmodel.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class ProjectRepository {

    val gitHubService by lazy {
        GitHubService.create()
    }

    fun getUser(username: String): MutableLiveData<UserHandler> {
        val userHandler: MutableLiveData<UserHandler> = SingleLiveEvent()

        gitHubService.getUser("luiz-matias").enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        userHandler.value = UserHandler.Success(it)
                    }
                } else {
                    userHandler.value = UserHandler.Error(Throwable("Erro ao recuperar o usuário"))
                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                userHandler.value = UserHandler.Error(t)
            }
        })

        return userHandler
    }

}
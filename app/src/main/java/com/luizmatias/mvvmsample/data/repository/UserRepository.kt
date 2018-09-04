package com.luizmatias.mvvmsample.data.repository

import android.content.Context
import com.luizmatias.mvvmsample.data.model.User
import com.luizmatias.mvvmsample.data.repository.local.UserDatabase
import com.luizmatias.mvvmsample.data.repository.remote.WebService
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepository(context: Context) {

    companion object {
        private lateinit var instance: UserRepository

        @Synchronized
        fun getInstance(context: Context): UserRepository {
            instance = UserRepository(context)
            return instance
        }
    }

    private val webService by lazy {
        WebService.getInstance().create()
    }
    private val database by lazy {
        UserDatabase.getInstance(context)
    }


    fun getUserByLoginLocal(login: String): Flowable<User> {
        getUserByLoginRemote(login)
        return database.userDAO().getByLogin(login)
    }

    private fun getUserByLoginRemote(login: String) {
        webService.getUser(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    //início do loading
                }
                .doOnTerminate {
                    //término do loading
                }
                .subscribe({
                    //retornou com sucesso o response (nesse caso, um objeto User já serializado)
                    database.userDAO().add(it)
                }, {
                    //ocorreu um erro na request
                })
    }

}
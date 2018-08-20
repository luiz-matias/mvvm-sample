package com.luizmatias.mvvmsample.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import com.luizmatias.mvvmsample.model.User
import com.luizmatias.mvvmsample.repository.ApiFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val api by lazy {
        ApiFactory.create()
    }
    val homeStateHandler = SingleLiveEvent<HomeStateHandler>()
    private lateinit var subscription: Disposable
    private val user: MutableLiveData<User> = MutableLiveData()
    var isLoading: ObservableField<Boolean> = ObservableField()

    fun getUser(): MutableLiveData<User> {
        return user
    }

    fun loadUser(username: String) {
        if(username.isNotEmpty()){
            homeStateHandler.value = HomeStateHandler.setSearchError(false)
            subscription = api.getUser(username)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        //início do loading
                        setLoading(true)
                    }
                    .doOnTerminate {
                        //término do loading
                        setLoading(false)
                    }
                    .subscribe({
                        //retornou com sucesso o response (nesse caso, um objeto User já serializado)
                        onRetrievePostListSuccess(it)
                    }, {
                        //ocorreu um erro na request
                        
                        onRetrievePostListError(it)
                    })
        } else {
            homeStateHandler.value = HomeStateHandler.setSearchError(true)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }

    private fun onRetrievePostListSuccess(user: User) {
        this.user.postValue(user)
    }

    private fun onRetrievePostListError(throwable: Throwable) {
        throwable.message?.let {
            homeStateHandler.value = HomeStateHandler.setError(it)
        }
    }

}
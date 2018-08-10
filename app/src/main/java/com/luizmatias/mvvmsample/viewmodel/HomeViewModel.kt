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
    private lateinit var user: MutableLiveData<User>
    var isLoading: ObservableField<Boolean> = ObservableField()

    fun getUser(): MutableLiveData<User> {
        return user
    }

    fun loadUser(username: String) {
        subscription = api.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { setLoading(true) }
                .doOnTerminate { setLoading(false) }
                .subscribe({
                    onRetrievePostListSuccess(it)
                }, {
                    onRetrievePostListError(it)
                })
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
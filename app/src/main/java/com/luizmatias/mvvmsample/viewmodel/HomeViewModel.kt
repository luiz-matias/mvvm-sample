package com.luizmatias.mvvmsample.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.databinding.ObservableField
import com.luizmatias.mvvmsample.data.model.User
import com.luizmatias.mvvmsample.data.repository.UserRepository
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    val homeStateHandler = SingleLiveEvent<HomeStateHandler>()
    private lateinit var subscription: Disposable
    private val user: MutableLiveData<User> = MutableLiveData()
    private val repository: UserRepository = UserRepository.getInstance(getApplication() as Context)
    var isLoading: ObservableField<Boolean> = ObservableField()

    fun getUser(): MutableLiveData<User> {
        return user
    }

    fun loadUser(username: String) {
        if (username.isNotEmpty()) {
            homeStateHandler.value = HomeStateHandler.setSearchError(false)
            subscription = repository.getUserByLoginLocal(username)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe {
                        setLoading(true)
                    }
                    .doOnTerminate {
                        setLoading(false)
                    }
                    .subscribe({
                        onRetrievePostListSuccess(it)
                    }, {
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

    override fun onCleared() {
        subscription.dispose()
        super.onCleared()
    }
}
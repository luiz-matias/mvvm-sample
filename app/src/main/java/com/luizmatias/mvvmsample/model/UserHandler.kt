package com.luizmatias.mvvmsample.model

import android.arch.lifecycle.MutableLiveData

sealed class UserHandler{
    class Success(val user : User) : UserHandler()
    class Error(val error: Throwable) : UserHandler()
}
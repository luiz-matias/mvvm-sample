package com.luizmatias.mvvmsample.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.luizmatias.mvvmsample.SingleLiveEvent
import com.luizmatias.mvvmsample.postDelayed

class LoginViewModel(app: Application) : AndroidViewModel(app) {
    val loginStateHandler = SingleLiveEvent<LoginStateHandler>()

    fun validarCredenciais(email: String, senha: String) {
        var loginValido = true

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginStateHandler.value = LoginStateHandler.setEmailError(true)
            loginValido = false
        } else {
            loginStateHandler.value = LoginStateHandler.setEmailError(false)
        }

        if (senha.isEmpty() || senha.length < 6) {
            loginStateHandler.value = LoginStateHandler.setSenhaError(true)
            loginValido = false
        } else {
            loginStateHandler.value = LoginStateHandler.setSenhaError(false)
        }

        if (loginValido) login(email, senha)
    }

    fun login(email: String, senha: String){
        //TODO Request de login para a Model
        loginStateHandler.value = LoginStateHandler.setCarregando(true)
        postDelayed(1500) {
            when {
                email == "devmaker@devmaker.com" && senha == "123456" -> loginStateHandler.value = LoginStateHandler.navegarParaHome()
                else -> loginStateHandler.value = LoginStateHandler.setLoginError()
            }
            loginStateHandler.value = LoginStateHandler.setCarregando(true)
        }
    }

}
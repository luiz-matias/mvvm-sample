package com.luizmatias.mvvmsample.viewmodel

sealed class LoginStateHandler{
    class setCarregando(val carregando: Boolean) : LoginStateHandler()
    class setEmailError(val erro: Boolean) : LoginStateHandler()
    class setSenhaError(val erro: Boolean) : LoginStateHandler()
    class setLoginError : LoginStateHandler()
    class navegarParaHome : LoginStateHandler()
}
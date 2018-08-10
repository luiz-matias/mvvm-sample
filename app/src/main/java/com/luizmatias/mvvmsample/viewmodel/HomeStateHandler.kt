package com.luizmatias.mvvmsample.viewmodel

sealed class HomeStateHandler {
    class setCarregando(val carregando: Boolean) : HomeStateHandler()
    class setError(val erro: String) : HomeStateHandler()
}
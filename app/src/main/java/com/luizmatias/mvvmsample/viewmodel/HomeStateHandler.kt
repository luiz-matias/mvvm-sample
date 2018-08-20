package com.luizmatias.mvvmsample.viewmodel

sealed class HomeStateHandler {
    class setSearchError(val error: Boolean) : HomeStateHandler()
    class setError(val erro: String) : HomeStateHandler()
}
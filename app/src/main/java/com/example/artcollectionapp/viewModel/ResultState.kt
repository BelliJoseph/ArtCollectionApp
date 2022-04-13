package com.example.artcollectionapp.viewModel

sealed class ResultState{
    object LOADING: ResultState()
    object DEFAULT: ResultState()
    class SUCCESS<T>(val response: T): ResultState()
    class ERROR(val error: Throwable): ResultState()
}
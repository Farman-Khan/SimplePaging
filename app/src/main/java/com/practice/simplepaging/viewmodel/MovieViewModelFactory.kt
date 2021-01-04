package com.practice.simplepaging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.simplepaging.api.RetrofitApiFactory

class MovieViewModelFactory() :
     ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(RetrofitApiFactory.retrofitApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
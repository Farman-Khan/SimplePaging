package com.practice.simplepaging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.practice.simplepaging.api.RetrofitApi
import com.practice.simplepaging.data.MovieDataSource

class MovieViewModel(private val api: RetrofitApi) : ViewModel() {

    val movieData = Pager(PagingConfig(pageSize = 6)) {
        MovieDataSource(api)
    }.flow.cachedIn(viewModelScope)
}
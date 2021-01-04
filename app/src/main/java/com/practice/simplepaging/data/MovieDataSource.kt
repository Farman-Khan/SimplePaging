package com.practice.simplepaging.data

import androidx.paging.PagingSource
import com.practice.simplepaging.api.RetrofitApi
import com.practice.simplepaging.data.gson.Movie

class MovieDataSource(private val api: RetrofitApi) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val nextPage = params.key ?: 1
            val response = api.getPopularMovies(nextPage)
            val data = response.body()?.movies ?: emptyList()

            return LoadResult.Page(
                data = data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}
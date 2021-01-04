package com.practice.simplepaging.api

import com.practice.simplepaging.data.gson.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    // https://api.themoviedb.org/3/movie/popular?api_key=[api_key]&page=1

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") pageNumber: Int) : Response<MoviesResponse>
}
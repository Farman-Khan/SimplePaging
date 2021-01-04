package com.practice.simplepaging.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.practice.simplepaging.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApiFactory {
    private const val API_KEY = BuildConfig.MOVIE_API_KEY
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    //creating Auth interceptor common to all api requests
    private val authInterceptor = Interceptor {chain ->

        val requestUrl = chain.request()
            .url
            .newBuilder().addQueryParameter("api_key", API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(requestUrl)
            .build()
        chain.proceed(request)
    }

    private var loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
        level = HttpLoggingInterceptor.Level.BODY
    }

    //Okhttp client for http request url
    private val requestClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    /*   private val moshi = Moshi.Builder()
           .add(KotlinJsonAdapterFactory())
           .build()

       //creating retrofit provider
       fun retrofit(): Retrofit {

           return Retrofit.Builder()
               .client(requestClient)
               .baseUrl(BASE_URL)
               .addConverterFactory(MoshiConverterFactory.create(moshi))
               .addCallAdapterFactory(CoroutineCallAdapterFactory())
               .build()
       }*/

    fun retrofit(): Retrofit {

        return Retrofit.Builder()
            .client(requestClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    val retrofitApi: RetrofitApi = retrofit().create(RetrofitApi::class.java)
}


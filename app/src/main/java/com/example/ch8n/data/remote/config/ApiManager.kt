package com.example.ch8n.data.remote.config

import com.example.ch8n.data.remote.sources.MovieService
import retrofit2.Retrofit

class ApiManager(private val retrofit : Retrofit) {

    val movieService : MovieService by lazy {
        retrofit.create(MovieService::class.java)
    }

}
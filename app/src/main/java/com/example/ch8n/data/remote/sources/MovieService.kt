package com.example.ch8n.data.remote.sources

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/")
    fun searchMovie(@Query("apikey") apiKey : String, @Query("s") query : String) : Single<MovieSearchResponse>

    @GET("/")
    fun getMovie(@Query("apikey") apiKey : String, @Query("i") movieId : String) : Single<MovieDetailResponse>
}
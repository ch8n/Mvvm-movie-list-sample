package com.example.ch8n.data.remote.sources

import io.reactivex.Single

class MovieSource(val apiKey : String, val movieService : MovieService) {

    fun searchMovie(query : String): Single<MovieSearchResponse> {
        return movieService.searchMovie(apiKey, query)
    }

    fun getMovie(query : String): Single<MovieDetailResponse> {
      return  movieService.getMovie(apiKey, query)
    }
}
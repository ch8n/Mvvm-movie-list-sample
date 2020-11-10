package com.example.ch8n.di

import androidx.appcompat.app.AppCompatActivity
import com.example.ch8n.detail.MovieDetailViewModel
import com.example.ch8n.search.MovieSearchViewModel

object Injector {

    private val okHttp = Resolver.provideOkHttpClient()
    private val retrofit = Resolver.provideRetrofitClient(okHttp)
    val apiKey = Resolver.provideApiKey()
    private val apiManager = Resolver.provideApiManager(retrofit)

    private val movieSource by lazy {
        Resolver.provideMovieDataSource(
            apiKey,
            apiManager
        )
    }

    private val movieRepository by lazy {
        Resolver.provideMovieRepository(movieSource)
    }

    private val searchViewModelFactory by lazy {
        Resolver.provideSearchViewModelFactory(movieRepository)
    }

    fun searchViewModel(activity: AppCompatActivity) : MovieSearchViewModel {
        return Resolver.provideSearchViewModel(searchViewModelFactory, activity)
    }

    private val detailViewModelFactory by lazy {
        Resolver.provideDetailViewModelFactory(movieRepository)
    }

    fun detailViewModel(activity: AppCompatActivity) : MovieDetailViewModel {
        return Resolver.provideDetailViewModel(detailViewModelFactory, activity)
    }
}

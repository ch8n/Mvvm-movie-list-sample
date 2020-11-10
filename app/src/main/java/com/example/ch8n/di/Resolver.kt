package com.example.ch8n.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.ch8n.data.remote.config.ApiManager
import com.example.ch8n.data.remote.config.BaseUrl
import com.example.ch8n.data.remote.sources.MovieSource
import com.example.ch8n.data.repository.MovieRepository
import com.example.ch8n.detail.MovieDetailViewModel
import com.example.ch8n.detail.factory.MovieDetailViewModelFactory
import com.example.ch8n.search.MovieSearchViewModel
import com.example.ch8n.search.factories.MovieSearchViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Resolver {

    fun provideOkHttpClient() : OkHttpClient = OkHttpClient
        .Builder()
        .build()

    fun provideRetrofitClient(okHttpClient : OkHttpClient) : Retrofit{
       return Retrofit.Builder()
            .baseUrl(BaseUrl.dev)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun provideApiManager(retrofit: Retrofit) = ApiManager(retrofit)

    fun provideApiKey() : String = "b6573989"

    fun provideMovieDataSource(apiKey : String, apiManager : ApiManager) : MovieSource = MovieSource(apiKey, apiManager.movieService)

    fun provideMovieRepository(movieSource: MovieSource) : MovieRepository = MovieRepository(movieSource)

    fun provideSearchViewModelFactory(movieRepository : MovieRepository) = MovieSearchViewModelFactory(movieRepository)

    fun provideSearchViewModel(factory : MovieSearchViewModelFactory, activity : FragmentActivity) = ViewModelProviders
        .of(activity, factory)
        .get(MovieSearchViewModel::class.java)

    fun provideDetailViewModelFactory(movieRepository : MovieRepository) = MovieDetailViewModelFactory(movieRepository)

    fun provideDetailViewModel(factory : MovieDetailViewModelFactory, activity : FragmentActivity) = ViewModelProviders
        .of(activity, factory)
        .get(MovieDetailViewModel::class.java)

}
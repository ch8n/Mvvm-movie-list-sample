package com.example.ch8n.di.modules

import com.example.ch8n.data.remote.config.ApiManager
import com.example.ch8n.data.remote.sources.MovieSource
import com.example.ch8n.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataSourceResolver {


    @Singleton
    @Provides
    fun provideMovieDataSource(    @Named("apikey")
                                   apiKey : String, apiManager : ApiManager) : MovieSource = MovieSource(apiKey, apiManager.movieService)

    @Singleton
    @Provides
    fun provideMovieRepository(movieSource: MovieSource) : MovieRepository = MovieRepository(movieSource)

}
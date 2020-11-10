package com.example.ch8n.search.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.ch8n.data.repository.MovieRepository
import com.example.ch8n.search.MovieSearchActivity
import com.example.ch8n.search.MovieSearchViewModel
import com.example.ch8n.search.factories.MovieSearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MovieSearchModule{

    @Provides
    fun provideSearchViewModelFactory(movieRepository : MovieRepository) = MovieSearchViewModelFactory(movieRepository)

    @Provides
    fun provideSearchViewModel(factory : MovieSearchViewModelFactory, activity : MovieSearchActivity) = ViewModelProviders
        .of(activity as AppCompatActivity, factory)
        .get(MovieSearchViewModel::class.java)

}
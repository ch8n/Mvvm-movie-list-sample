package com.example.ch8n.detail.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.ch8n.data.repository.MovieRepository
import com.example.ch8n.detail.MovieDetailActivity
import com.example.ch8n.detail.MovieDetailViewModel
import com.example.ch8n.detail.factory.MovieDetailViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MovieDetailModule{

    @Provides
    fun provideDetailViewModelFactory(movieRepository : MovieRepository) = MovieDetailViewModelFactory(movieRepository)

    @Provides
    fun provideDetailViewModel(factory : MovieDetailViewModelFactory, activity : MovieDetailActivity) = ViewModelProviders
        .of(activity as AppCompatActivity, factory)
        .get(MovieDetailViewModel::class.java)

}
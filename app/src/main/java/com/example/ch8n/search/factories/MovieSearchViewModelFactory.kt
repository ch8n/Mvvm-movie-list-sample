package com.example.ch8n.search.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ch8n.data.repository.MovieRepository
import com.example.ch8n.search.MovieSearchViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieSearchViewModelFactory @Inject constructor(private val movieRepository: MovieRepository) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieSearchViewModel(movieRepository) as T

    }

}
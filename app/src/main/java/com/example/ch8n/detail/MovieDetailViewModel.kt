package com.example.ch8n.detail

import androidx.lifecycle.ViewModel
import com.example.ch8n.data.remote.sources.MovieDetailRequest
import com.example.ch8n.data.remote.sources.MovieDetailResponse
import com.example.ch8n.data.remote.sources.Rating
import com.example.ch8n.data.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel(val movieRespository : MovieRepository) : ViewModel() {

    fun getMovieDetail(movieId : String): Single<MovieDetailResponse> {
        val detailRequest = MovieDetailRequest(movieId)
         return movieRespository.getMovieDetail(detailRequest)
             .map {
                 val ratings = it.ratings?.map {
                    val rating = "${it.source} : ${it.value}"
                     Rating(source = rating, value = null)
                 }
                 it.copy(ratings = ratings)
             }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}
package com.example.ch8n.search

import androidx.lifecycle.ViewModel
import com.example.ch8n.data.remote.sources.MovieSearchRequest
import com.example.ch8n.data.repository.MovieRepository
import com.example.ch8n.search.adapter.SearchListItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalStateException
import java.util.concurrent.TimeUnit

class MovieSearchViewModel(val movieRespository : MovieRepository) : ViewModel() {

    fun searchMovie(query : String): Observable<List<SearchListItem>> {
         return Observable.just(query)
             .map{if (it.isEmpty()){
                  return@map throw IllegalStateException("empty string")
             }
                 return@map it
             }
             .debounce(500, TimeUnit.MILLISECONDS)
             .flatMap {
                 val searchRequest = MovieSearchRequest(it)
                 movieRespository.searchMovie(searchRequest).toObservable()
             }.map {response ->
                response.search?.map {movieDetail ->
                    SearchListItem(
                        imageUrl = movieDetail.poster ?: "",
                        movieTitle = movieDetail?.title ?: "",
                        movieReleaseYear = movieDetail.year ?: "",
                        movieId = movieDetail.imdbID ?: "",
                        movieType = movieDetail.type ?: ""
                    )
                }?: emptyList()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}
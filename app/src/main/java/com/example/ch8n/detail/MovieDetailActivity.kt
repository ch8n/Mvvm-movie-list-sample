package com.example.ch8n.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ch8n.R
import com.example.ch8n.data.remote.sources.MovieDetailResponse
import com.example.ch8n.detail.adapter.MovieDetailTextAdapter
import com.example.ch8n.detail.adapter.TextListItem
import com.example.ch8n.search.addDisposer
import com.example.ch8n.utils.loadImage
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //   val viewModel = Injector.detailViewModel(this)

        viewModel.getMovieDetail(getMovieId())
            .subscribe({
                onResult(it)
                progress.visibility = View.GONE
            }, {
                onErrorResult(it)
                progress.visibility = View.GONE
            })
            .addDisposer(compositeDisposable)


        image_back.setOnClickListener {
            finish()
        }

    }

    fun getMovieId(): String {
        val movie_id =  intent.getStringExtra(MOVIE_ID)?:""
        if (movie_id.isEmpty()){
            Toast.makeText(this, MOVIE_ID_ERROR, Toast.LENGTH_SHORT).show()
            finish()
        }
        return movie_id
    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun onResult(movieDetail : MovieDetailResponse){
        Timber.d("Movie Detail=$movieDetail")
        val movieActors = movieDetail.actors?.split(", ")?.map {
            TextListItem(
                text = it
            )
        }?: emptyList()
        val movieDirectors = movieDetail.director?.split(", ")?.map {
            TextListItem(
                text = it
            )
        }?: emptyList()
        val movieWriters = movieDetail.writer?.split(", ")?.map {
            TextListItem(
                text = it
            )
        }?: emptyList()
        val ratings = movieDetail.ratings?.map {
            TextListItem(
                text = it.source?:""
            )
        }?: emptyList()

        container_movie_detail.visibility = View.VISIBLE

        with(container_movie_detail){
            image_banner.loadImage(movieDetail.poster?:"")
            text_title.setText("${movieDetail.title?:""}")
            text_release_date.setText("$MOVIE_YEAR : ${movieDetail.released?:""}")
            text_plot.setText(movieDetail.plot?:"")
            text_genre.setText("$MOVIE_GENRE : ${movieDetail.genre?:""}")
            text_language.setText("$MOVIE_LANGUAGE : ${movieDetail.language?:""}")
            text_runtime.setText("$MOVIE_RUNTIME : ${movieDetail.runtime?:""}")
            text_production.setText("$MOVIE_PRODUCTION : ${movieDetail.production?:""}")
            text_imdb_rating.setText("$MOVIE_IMDB_RATING : ${movieDetail.imdbRating?:""}")
            text_imdb_votes.setText("$MOVIE_IMDB_VOTES : ${movieDetail.imdbVotes?:""}")
        }

        if (!ratings.isEmpty()) {
            list_ratings.adapter = MovieDetailTextAdapter.newInstance().also {
                it.submitList(ratings)
            }
        }else{
            cv_rating.visibility = View.GONE
        }

        if (!movieDirectors.isEmpty()) {
            list_director.adapter = MovieDetailTextAdapter.newInstance().also {

                it.submitList(movieDirectors)
            }
        }else{
            cv_director.visibility = View.GONE
        }

        if (!movieWriters.isEmpty()) {
            list_writers.adapter = MovieDetailTextAdapter.newInstance().also {
                it.submitList(movieWriters)
            }
        }else{
            cv_writer.visibility = View.GONE
        }

        if (!movieActors.isEmpty()) {
            list_actors.adapter = MovieDetailTextAdapter.newInstance().also {
                it.submitList(movieActors)
            }
        }else{
            cv_actor.visibility = View.GONE
        }

        Timber.d(movieDetail.toString())
    }

    fun onErrorResult(error : Throwable){
        Timber.e(error)
        Toast.makeText(this, UNKNOWN_ERROR, Toast.LENGTH_SHORT).show()
        finish()
    }


    companion object{
        const val MOVIE_ID = "movie_id"
        const val MOVIE_ID_ERROR = "Invalid Movie Id"
        const val UNKNOWN_ERROR = "Something went wrong !"

        const val MOVIE_YEAR = "Movie Year"
        const val MOVIE_RUNTIME = "Runtime"
        const val MOVIE_GENRE = "Genre"
        const val MOVIE_LANGUAGE = "Language"
        const val MOVIE_COUNTRY = "Movie Country"
        const val MOVIE_IMDB_RATING = "imdb rating"
        const val MOVIE_IMDB_VOTES = "imdb votes"
        const val MOVIE_PRODUCTION = "Production"

    }
}

package com.example.ch8n.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ch8n.R
import com.example.ch8n.data.remote.sources.MovieDetailResponse
import com.example.ch8n.databinding.ActivityDetailBinding
import com.example.ch8n.detail.adapter.MovieDetailTextAdapter
import com.example.ch8n.detail.adapter.TextListItem
import com.example.ch8n.search.addDisposer
import com.example.ch8n.utils.loadImage
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModel: MovieDetailViewModel

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        //   val viewModel = Injector.detailViewModel(this)

        viewModel.getMovieDetail(getMovieId())
            .subscribe({
                onResult(it)
                binding.progress.visibility = View.GONE
            }, {
                onErrorResult(it)
                binding.progress.visibility = View.GONE
            })
            .addDisposer(compositeDisposable)


        binding.imageBack.setOnClickListener {
            finish()
        }

    }

    fun getMovieId(): String {
        val movie_id = intent.getStringExtra(MOVIE_ID) ?: ""
        if (movie_id.isEmpty()) {
            Toast.makeText(this, MOVIE_ID_ERROR, Toast.LENGTH_SHORT).show()
            finish()
        }
        return movie_id
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun onResult(movieDetail: MovieDetailResponse) {
        Timber.d("Movie Detail=$movieDetail")
        val movieActors = movieDetail.actors?.split(", ")?.map {
            TextListItem(
                text = it
            )
        } ?: emptyList()
        val movieDirectors = movieDetail.director?.split(", ")?.map {
            TextListItem(
                text = it
            )
        } ?: emptyList()
        val movieWriters = movieDetail.writer?.split(", ")?.map {
            TextListItem(
                text = it
            )
        } ?: emptyList()
        val ratings = movieDetail.ratings?.map {
            TextListItem(
                text = it.source ?: ""
            )
        } ?: emptyList()

        binding.containerMovieDetail.visibility = View.VISIBLE

        with(binding.containerMovieDetail) {
            binding.imageBanner.loadImage(movieDetail.poster ?: "")
            binding.textTitle.setText("${movieDetail.title ?: ""}")
            binding.textReleaseDate.setText("$MOVIE_YEAR : ${movieDetail.released ?: ""}")
            binding.textPlot.setText(movieDetail.plot ?: "")
            binding.textGenre.setText("$MOVIE_GENRE : ${movieDetail.genre ?: ""}")
            binding.textLanguage.setText("$MOVIE_LANGUAGE : ${movieDetail.language ?: ""}")
            binding.textRuntime.setText("$MOVIE_RUNTIME : ${movieDetail.runtime ?: ""}")
            binding.textProduction.setText("$MOVIE_PRODUCTION : ${movieDetail.production ?: ""}")
            binding.textImdbRating.setText("$MOVIE_IMDB_RATING : ${movieDetail.imdbRating ?: ""}")
            binding.textImdbVotes.setText("$MOVIE_IMDB_VOTES : ${movieDetail.imdbVotes ?: ""}")
        }

        if (!ratings.isEmpty()) {
            binding.listRatings.adapter = MovieDetailTextAdapter.newInstance().also {
                it.submitList(ratings)
            }
        } else {
            binding.cvRating.visibility = View.GONE
        }

        if (!movieDirectors.isEmpty()) {
            binding.listDirector.adapter = MovieDetailTextAdapter.newInstance().also {

                it.submitList(movieDirectors)
            }
        } else {
            binding.cvDirector.visibility = View.GONE
        }

        if (!movieWriters.isEmpty()) {
            binding.listWriters.adapter = MovieDetailTextAdapter.newInstance().also {
                it.submitList(movieWriters)
            }
        } else {
            binding.cvWriter.visibility = View.GONE
        }

        if (!movieActors.isEmpty()) {
            binding.listActors.adapter = MovieDetailTextAdapter.newInstance().also {
                it.submitList(movieActors)
            }
        } else {
            binding.cvActor.visibility = View.GONE
        }

        Timber.d(movieDetail.toString())
    }

    fun onErrorResult(error: Throwable) {
        Timber.e(error)
        Toast.makeText(this, UNKNOWN_ERROR, Toast.LENGTH_SHORT).show()
        finish()
    }


    companion object {
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

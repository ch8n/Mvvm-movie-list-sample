package com.example.ch8n.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.ch8n.search.adapter.MovieSearchAdapter
import com.example.ch8n.R
import com.example.ch8n.databinding.ActivityMovieSearchBinding
import com.example.ch8n.detail.MovieDetailActivity
import com.example.ch8n.search.adapter.SearchListItem
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class MovieSearchActivity : AppCompatActivity() {

    private lateinit var movieSearchAdapter: MovieSearchAdapter
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var binding: ActivityMovieSearchBinding

    @Inject
    lateinit var viewModel: MovieSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMovieSearchBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        val onSearchItemClick = { position: Int ->
            val searchItem = movieSearchAdapter.getItemAt(position)
            if (searchItem != null) {
                startActivity(
                    Intent(
                        this@MovieSearchActivity,
                        MovieDetailActivity::class.java
                    ).also {
                        it.putExtra(MovieDetailActivity.MOVIE_ID, searchItem.movieId)
                    })

            } else {
                Toast.makeText(
                    this@MovieSearchActivity,
                    MovieDetailActivity.MOVIE_ID_ERROR,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.listMovie.adapter = MovieSearchAdapter.newInstance(onSearchItemClick)
            .also {
                movieSearchAdapter = it
            }

        binding.editMovieQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                val query = editable.toString()
                binding.progress.visibility = View.VISIBLE
                viewModel.searchMovie(query)
                    .subscribe({
                        onSearchResult(it)
                        binding.progress.visibility = View.GONE
                    }, {
                        onErrorResult(it)
                        binding.progress.visibility = View.GONE
                    })
                    .addDisposer(compositeDisposable)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        binding.imageBack.setOnClickListener {
            finish()
        }
    }

    fun onSearchResult(items: List<SearchListItem>) {
        if (items.isEmpty()) {
            binding.textError.visibility = View.VISIBLE
            binding.progress.visibility = View.GONE
            return
        }
        binding.textError.visibility = View.GONE
        movieSearchAdapter.submitList(items)
    }

    fun onErrorResult(error: Throwable) {
        Timber.e(error)
        binding.textError.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()

    }

}

fun Disposable.addDisposer(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

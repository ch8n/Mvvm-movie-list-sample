package com.example.ch8n.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.ch8n.search.adapter.MovieSearchAdapter
import com.example.ch8n.R
import com.example.ch8n.detail.MovieDetailActivity
import com.example.ch8n.search.adapter.SearchListItem
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_movie_search.*
import timber.log.Timber
import javax.inject.Inject

class MovieSearchActivity : AppCompatActivity() {

    private lateinit var movieSearchAdapter : MovieSearchAdapter
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModel: MovieSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)

    //    val viewModel = Injector.searchViewModel(this)

        Timber.plant(Timber.DebugTree())

        val onSearchItemClick = {position : Int ->
           val searchItem = movieSearchAdapter.getItemAt(position)
            if (searchItem != null){
                startActivity(Intent(this@MovieSearchActivity, MovieDetailActivity::class.java).also {
                    it.putExtra(MovieDetailActivity.MOVIE_ID, searchItem.movieId)
                })

            }else{
                Toast.makeText(this@MovieSearchActivity, MovieDetailActivity.MOVIE_ID_ERROR, Toast.LENGTH_SHORT).show()
            }
        }

        list_movie.adapter = MovieSearchAdapter.newInstance(onSearchItemClick)
            .also {
                movieSearchAdapter = it
            }

        edit_movie_query.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
              val query = editable.toString()
                    progress.visibility = View.VISIBLE
                    viewModel.searchMovie(query)
                        .subscribe({
                            onSearchResult(it)
                            progress.visibility = View.GONE
                        }, {
                            onErrorResult(it)
                            progress.visibility = View.GONE
                        })
                        .addDisposer(compositeDisposable)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        image_back.setOnClickListener {
            finish()
        }
    }

    fun onSearchResult(items : List<SearchListItem>){
        if (items.isEmpty()){
            text_error.visibility = View.VISIBLE
            progress.visibility = View.GONE
            return
        }
        text_error.visibility = View.GONE
        movieSearchAdapter.submitList(items)
    }

    fun onErrorResult(error : Throwable){
        Timber.e(error)
        text_error.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()

    }

}

     fun Disposable.addDisposer(compositeDisposable: CompositeDisposable) {
        compositeDisposable.add(this)
    }

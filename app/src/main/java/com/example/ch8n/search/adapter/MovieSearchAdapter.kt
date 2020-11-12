package com.example.ch8n.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ch8n.databinding.SearchMovieItemBinding
import com.example.ch8n.utils.cancelImageLoading
import com.example.ch8n.utils.loadImage

class MovieSearchAdapter private constructor(
    diffUtil: DiffUtil.ItemCallback<SearchListItem>,
    private val onItemClick: (Int) -> Unit
) : ListAdapter<SearchListItem, MovieItemVH>(diffUtil) {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchListItem>() {

            override fun areItemsTheSame(
                oldItem: SearchListItem,
                newItem: SearchListItem
            ): Boolean =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(
                oldItem: SearchListItem,
                newItem: SearchListItem
            ): Boolean =
                oldItem == newItem

        }

        fun newInstance(onItemClick: (Int) -> Unit) = MovieSearchAdapter(
            DIFF_CALLBACK, onItemClick
        )
    }

    fun getItemAt(position: Int): SearchListItem? {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemVH {
        val binding =
            SearchMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MovieItemVH(binding)
    }

    override fun onBindViewHolder(holder: MovieItemVH, position: Int) {
        holder.bind(requireNotNull(getItemAt(position)), onItemClick)
    }

    override fun onViewAttachedToWindow(holder: MovieItemVH) {
        super.onViewAttachedToWindow(holder)
        holder.loadMoviePoster()
    }

    override fun onViewDetachedFromWindow(holder: MovieItemVH) {
        super.onViewDetachedFromWindow(holder)
        holder.cancelLoading()
    }

}

class MovieItemVH(binding: SearchMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    var searchListItem: SearchListItem? = null
    val image_movie = binding.imageMovie
    val text_movie_title = binding.textMovieTitle
    val text_movie_year = binding.textMovieYear
    val text_movie_type = binding.textMovieType

    fun bind(
        searchListItem: SearchListItem,
        onItemClick: (Int) -> Unit
    ) {
        this.searchListItem = searchListItem
        image_movie.loadImage(searchListItem.imageUrl)
        text_movie_title.setText(searchListItem.movieTitle)
        text_movie_year.setText(searchListItem.movieReleaseYear)
        text_movie_type.setText(searchListItem.movieType)

        itemView.setOnClickListener {
            onItemClick.invoke(adapterPosition)
        }
    }

    fun cancelLoading() {
        image_movie.cancelImageLoading()
    }

    fun loadMoviePoster() {
        image_movie.loadImage(searchListItem?.imageUrl ?: "")
    }

}

data class SearchListItem(
    val imageUrl: String,
    val movieTitle: String,
    val movieReleaseYear: String,
    val movieId: String,
    val movieType: String
)
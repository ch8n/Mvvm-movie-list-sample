package com.example.ch8n.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ch8n.R
import com.example.ch8n.utils.cancelImageLoading
import com.example.ch8n.utils.loadImage
import kotlinx.android.synthetic.main.search_movie_item.view.*

class MovieSearchAdapter private constructor(
    private val diffUtil: DiffUtil.ItemCallback<SearchListItem>,
    private val onItemClick: (Int) -> Unit
) : ListAdapter<SearchListItem, MovieItemVH>(diffUtil){

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchListItem>() {

            override fun areItemsTheSame(oldItem: SearchListItem, newItem: SearchListItem): Boolean =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: SearchListItem, newItem: SearchListItem): Boolean =
                oldItem == newItem

        }

        fun newInstance(onItemClick : (Int) -> Unit) = MovieSearchAdapter(
            DIFF_CALLBACK, onItemClick
        )
    }

    fun getItemAt(position: Int): SearchListItem? {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemVH {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.search_movie_item, parent, false)
        return MovieItemVH(view)
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

class MovieItemVH(view : View) : RecyclerView.ViewHolder(view){

    var searchListItem : SearchListItem? = null
    val image_movie = view.image_movie
    val text_movie_title = view.text_movie_title
    val text_movie_year = view.text_movie_year
    val text_movie_type = view.text_movie_type

    fun bind(
        searchListItem: SearchListItem,
        onItemClick: (Int) -> Unit
    ){
        this.searchListItem = searchListItem
        image_movie.loadImage(searchListItem.imageUrl)
        text_movie_title.setText(searchListItem.movieTitle)
        text_movie_year.setText(searchListItem.movieReleaseYear)
        text_movie_type.setText(searchListItem.movieType)

        itemView.setOnClickListener {
            onItemClick.invoke(adapterPosition)
        }
    }

    fun cancelLoading(){
        image_movie.cancelImageLoading()
    }

    fun loadMoviePoster(){
        image_movie.loadImage(searchListItem?.imageUrl?:"")
    }

}

data class SearchListItem(val imageUrl: String, val movieTitle : String, val movieReleaseYear : String, val movieId : String, val movieType : String)
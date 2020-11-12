package com.example.ch8n.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ch8n.databinding.MovieDetailItemsBinding

class MovieDetailTextAdapter private constructor(
    private val diffUtil: DiffUtil.ItemCallback<TextListItem>
) : ListAdapter<TextListItem, TextItemVH>(diffUtil){

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TextListItem>() {

            override fun areItemsTheSame(oldItem: TextListItem, newItem: TextListItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: TextListItem, newItem: TextListItem): Boolean =
                oldItem == newItem

        }

        fun newInstance() = MovieDetailTextAdapter(
            DIFF_CALLBACK
        )
    }

    fun getItemAt(position: Int): TextListItem? {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemVH {
        val binding = MovieDetailItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TextItemVH(binding)
    }

    override fun onBindViewHolder(holder: TextItemVH, position: Int) {
       holder.bind(requireNotNull(getItemAt(position)))
    }
    

}

class TextItemVH(binding: MovieDetailItemsBinding) : RecyclerView.ViewHolder(binding.root){

    val text_movie_item = binding.textMovieItem

    fun bind(
        textListItem: TextListItem
    ){

        text_movie_item.setText(textListItem.text)

    }
}

data class TextListItem(val text: String)
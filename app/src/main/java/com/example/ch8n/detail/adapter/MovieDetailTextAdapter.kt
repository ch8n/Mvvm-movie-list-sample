package com.example.ch8n.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ch8n.R
import kotlinx.android.synthetic.main.movie_detail_items.view.*

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
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_detail_items, parent, false)
        return TextItemVH(view)
    }

    override fun onBindViewHolder(holder: TextItemVH, position: Int) {
       holder.bind(requireNotNull(getItemAt(position)))
    }
    

}

class TextItemVH(view : View) : RecyclerView.ViewHolder(view){

    val text_movie_item = view.text_movie_item

    fun bind(
        textListItem: TextListItem
    ){

        text_movie_item.setText(textListItem.text)

    }
}

data class TextListItem(val text: String)
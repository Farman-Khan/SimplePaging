package com.practice.simplepaging.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.simplepaging.R

import com.practice.simplepaging.data.gson.Movie


class MovieListAdapter : PagingDataAdapter<Movie, MovieListAdapter.ViewHolder>(DataDifferntiator) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var poster: ImageView
        init {
            title = itemView.findViewById(R.id.textTitle)
            poster = itemView.findViewById(R.id.poster)
        }

        fun bindMovieUI(movie: Movie) {
            Log.d("Panda", "Loaded movie response: $movie")

            title.text = movie.title
            val url: String = "https://image.tmdb.org/t/p/w342" + movie.posterPath
            Glide.with(itemView.context)
                .load(url)
                .centerCrop()
                .into(poster)
        }
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindMovieUI(it)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.movie_list_item, parent, false)
        )
    }

    object DataDifferntiator : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

}
package net.apocalypt.modernmovies.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.list_item_movie.view.*
import net.apocalypt.modernmovies.R
import net.apocalypt.modernmovies.model.Result
import net.apocalypt.modernmovies.ui.movie.MovieActivity

class MainActivityAdapter(val ctx: Context) : ListAdapter<Result, MainActivityAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(result: Result) {
            with(itemView) {
                titleTextView.text = result.title
                vote_average_text.text = result.voteAverage.toString()

                result.posterPath?.let {
                    Glide.with(posterImage.context)
                        .load(POSTER_URL + result.posterPath)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(posterImage)
                }

                setOnClickListener {
                    val intent = Intent(ctx, MovieActivity::class.java)
                    intent.putExtra("movieId", result.id)
                    context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        const val POSTER_URL = "https://image.tmdb.org/t/p/w500"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem == newItem
        }
    }


}
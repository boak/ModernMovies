package net.apocalypt.modernmovies.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_movie_parallax.*
import kotlinx.android.synthetic.main.list_item_extracted.view.*
import kotlinx.android.synthetic.main.list_item_material_two_line.view.*
import net.apocalypt.modernmovies.R
import net.apocalypt.modernmovies.data.TMDBDatabase
import net.apocalypt.modernmovies.data.TMDBRepository
import net.apocalypt.modernmovies.data.network.TMDBService
import net.apocalypt.modernmovies.databinding.ActivityMovieParallaxBinding
import net.apocalypt.modernmovies.databinding.ListItemExtractedBinding
import net.apocalypt.modernmovies.model.movie.CastItem
import net.apocalypt.modernmovies.model.movie.MovieDetails
import net.apocalypt.modernmovies.ui.MainActivityViewModel
import net.apocalypt.modernmovies.ui.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class MovieActivity : AppCompatActivity() {

    var titleTitle = "None"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMovieParallaxBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_parallax)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = " "
        }

        // Hide title until collapsed
        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {

            var isCollapsed: Boolean = false

            override fun onOffsetChanged(appbarLayout: AppBarLayout?, i: Int) {
                if (Math.abs(i) == appbarLayout!!.totalScrollRange) {
                    collapsing_toolbar.isTitleEnabled = true
                    collapsing_toolbar.title = titleTitle
                    isCollapsed = true
                } else if (isCollapsed) {
                    collapsing_toolbar.isTitleEnabled = false
                    collapsing_toolbar.title = " "
                    isCollapsed = false
                }
            }
        })

        val database = TMDBDatabase.getInstance(this)
        val tmdbRepository = TMDBRepository(TMDBService.create(), database)

        val viewModel = ViewModelProviders.of(this, ViewModelFactory(tmdbRepository))
                .get(MainActivityViewModel::class.java)

        // 299536 - Infinity War (Default Test)
        val movieId: Int = intent?.let {
            it.extras?.getInt("movieId")
        } ?: 299536

        viewModel.getMovieDetails(movieId) { it ->

            recyclerMerged.adapter = UnifiedAdapter(it, it.credits?.cast)

            titleTitle = it.title!!
//
//            // data binding
//            with(binding) {
//                movieTitle = it.title
//            }
//
//            // normal
//            text_overview.text = it.overview
//            text_runtime.text = "${it.runtime} mins"
//
            Glide.with(image_backdrop.context)
                    .load("https://image.tmdb.org/t/p/w500" + it.backdropPath)
                    .into(image_backdrop)
//
//            // genre chips
//            it.genres?.forEach { genre ->
//                val chip = Chip(this)
//                chip.text = genre?.name
//                chip_group.addView(chip)
//            }
//
//            // release date
//            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
//            val date = simpleDateFormat.parse(it.releaseDate)
//            val calendar = Calendar.getInstance()
//            calendar.time = date
//
//            text_release_date.text = calendar.get(Calendar.YEAR).toString()


            // todo - remove and add to merged recyclerview as list_items
//            val castAdapter = CastAdapter(it.credits?.cast)
//            castRecycler.adapter = castAdapter
//            castRecycler.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

class UnifiedAdapter(var movie: MovieDetails, var cast: List<CastItem?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_TYPE_HEADER = 0
    val VIEW_TYPE_CAST = 1

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_HEADER
            else -> VIEW_TYPE_CAST
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val itemView2: ListItemExtractedBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                        R.layout.list_item_extracted,
                        parent,
                        false)

                HeaderViewHolder(itemView2)

//                val itemView = LayoutInflater
//                        .from(parent.context)
//                        .inflate(R.layout.list_item_extracted, parent, false)
//                HeaderViewHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.list_item_material_two_line, parent, false)
                CastViewHolder(itemView)
            }
        }
    }

    override fun getItemCount(): Int {
        //return cast?.size?.plus(1) ?: 1
        return movie.credits?.cast?.size?.plus(1) ?: 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_HEADER -> {
                (holder as HeaderViewHolder).bind(movie)
            }
            VIEW_TYPE_CAST -> {
                cast!![position - 1]?.let {
                    holder as CastViewHolder
                    holder.bind(it)
                }
            }
        }

    }

    class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cast: CastItem) {
            with(itemView) {
                primaryText.text = cast.name
                secondaryText.text = cast.character

                cast.profilePath?.let {profilePath ->
                    Glide.with(this)
                            .load("https://image.tmdb.org/t/p/w185$profilePath")
                            .apply(RequestOptions.circleCropTransform())
                            .into(avatar)
                }

            }
        }
    }

    val chipList: MutableList<Chip> = mutableListOf()

    inner class HeaderViewHolder(val itemViewBind: ListItemExtractedBinding) : RecyclerView.ViewHolder(itemViewBind.root) {

        fun bind(movie: MovieDetails) {

            itemViewBind.movieTitle2 = movie.title

            with(itemView.rootView) {
                text_overview.text = movie.overview
                text_runtime.text = "${movie.runtime ?: "?"} mins"

                if (chipList.isEmpty() && movie.genres!!.isNotEmpty()) {
                    movie.genres.forEach { genre ->
                        val chip = Chip(itemView.context)
                        chip.text = genre?.name
                        chipList.add(chip)
                        chip_group.addView(chip)
                    }
                }

                // release date
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val date = simpleDateFormat.parse(movie.releaseDate)
                val calendar = Calendar.getInstance()
                calendar.time = date

                text_release_date.text = calendar.get(Calendar.YEAR).toString()
            }


        }
    }


}


//class CastAdapter(var cast: List<CastItem?>?) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {
//    val VIEW_TYPE_HEADER = 0
//    val VIEW_TYPE_CAST = 1
//
//    override fun getItemViewType(position: Int): Int {
//        when (position) {
//            0 -> VIEW_TYPE_HEADER
//            else -> VIEW_TYPE_CAST
//        }
//        return super.getItemViewType(position)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastAdapter.ViewHolder {
//        when (viewType) {
//            VIEW_TYPE_HEADER -> {
//                val itemView = LayoutInflater
//                        .from(parent.context)
//                        .inflate(R.layout.list_item_extracted, parent, false)
//            }
//            VIEW_TYPE_CAST -> {
//                val itemView = LayoutInflater
//                        .from(parent.context)
//                        .inflate(R.layout.list_item_material_two_line, parent, false)
//                return ViewHolder(itemView)
//            }
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        return cast?.size ?: 0
//    }
//
//    override fun onBindViewHolder(holder: CastAdapter.ViewHolder, position: Int) {
//        cast!![position]?.let {
//            holder.bind(it)
//        }
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        fun bind(cast: CastItem) {
//            with(itemView) {
//                primaryText.text = cast.name
//                secondaryText.text = cast.character
//
//                Glide.with(this)
//                        .load("https://image.tmdb.org/t/p/w185" + cast.profilePath)
//                        .apply(RequestOptions.circleCropTransform())
//                        .into(avatar)
//            }
//        }
//    }
//
//    class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//
//        fun bind(movie: MovieDetails2) {
//
//        }
//    }
//
//
//}

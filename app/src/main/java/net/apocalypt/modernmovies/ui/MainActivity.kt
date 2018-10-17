package net.apocalypt.modernmovies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import net.apocalypt.modernmovies.R
import net.apocalypt.modernmovies.data.TMDBDatabase
import net.apocalypt.modernmovies.data.TMDBRepository
import net.apocalypt.modernmovies.data.network.TMDBService

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val database = TMDBDatabase.getInstance(this)
        val tmdbRepository = TMDBRepository(TMDBService.create(), database)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(tmdbRepository))
            .get(MainActivityViewModel::class.java)

        // Dump old data on each run
        viewModel.deleteAll()


        initAdapter()
        setupScrollListener()
    }

    private fun initAdapter() {
        val adapter = MainActivityAdapter(this)
        list.adapter = adapter

        viewModel.getData().observe(this, Observer { movieList ->
            movieList?.let {
                if (it.isEmpty()) {
                    viewModel.discover()
                }
            }

            adapter.submitList(movieList!!)
        })
    }

    // todo: need to save last page loaded somewhere or just dump each time?
    private fun setupScrollListener() {
        val layoutManager = list.layoutManager as LinearLayoutManager
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount, { println("callback") })
            }
        })
    }
}

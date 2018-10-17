package net.apocalypt.modernmovies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import net.apocalypt.modernmovies.data.TMDBRepository
import net.apocalypt.modernmovies.model.Result
import net.apocalypt.modernmovies.model.movie.MovieDetails

class MainActivityViewModel(private val repository: TMDBRepository): ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    fun getData() : LiveData<List<Result>> {
        return repository.getData()
    }

    fun discover() {
        repository.requestData()
    }

    fun deleteAll() {
        repository.dumpData()
    }

    fun getMovieDetails(movieId: Int, function: (results: MovieDetails) -> Unit) {
        repository.requestMovieDetails(movieId, function)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int, param: () -> Unit) {
        //println("visibleItemCount=$visibleItemCount + lastVisibleItemPosition=$lastVisibleItemPosition, VISIBLE_THRESHOLD=$VISIBLE_THRESHOLD >= totalItemCount=$totalItemCount")
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            discover()
        }
    }

}
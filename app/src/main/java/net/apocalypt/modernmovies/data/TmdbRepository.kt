package net.apocalypt.modernmovies.data

import androidx.lifecycle.LiveData
import net.apocalypt.modernmovies.data.network.TMDBService
import net.apocalypt.modernmovies.data.network.discover
import net.apocalypt.modernmovies.data.network.movieDetails
import net.apocalypt.modernmovies.model.Result
import net.apocalypt.modernmovies.model.movie.MovieDetails
import java.util.concurrent.Executors

class TMDBRepository(
        val remote: TMDBService,
        val local: TMDBDatabase
) {

    var page = 1
    var isRequestInProgress = false

    var discoverDao: DiscoverDao = local.discoverDao()

    fun insert(results: List<Result>) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            discoverDao.insert(results)
            page++
            isRequestInProgress = false
        }
    }

    fun requestData() {
        //println(discover(remote, 1))
        //discover(remote, 1) { println("in it: $it") }
        if (isRequestInProgress) {
            println("❗ request in progress")
            return
        }     // catch for scrolling

        isRequestInProgress = true
        discover(remote, page) {
            it.results?.let {
                println("inserting non-null data")
                insert(it)
//                page++
//                isRequestInProgress = false   // moved inside insert to help alievate race
            }
        }
    }

    fun requestMovieDetails(movieId: Int = 299536, function: (results: MovieDetails) -> Unit) {
        movieDetails(remote, movieId, function)
    }

    fun dumpData() {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            discoverDao.deleteAll()
        }
    }

    fun getData(): LiveData<List<Result>> {
        return local.discoverDao().getAll()
    }

    fun hello(): List<String> {
        return listOf("hello", "b")
    }

}
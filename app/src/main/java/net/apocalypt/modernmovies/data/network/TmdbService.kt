package net.apocalypt.modernmovies.data.network

import android.util.Log
import net.apocalypt.modernmovies.BuildConfig
import net.apocalypt.modernmovies.model.DiscoverResult
import net.apocalypt.modernmovies.model.movie.MovieDetails
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

fun discover(service: TMDBService,
             page: Int,
             onSuccess: (results: DiscoverResult) -> Unit) {
    Log.d("TMDBService", "OK")

    service.discover(page).enqueue(object : Callback<DiscoverResult> {
        override fun onFailure(call: Call<DiscoverResult>?, t: Throwable?) {
            TODO("not implemented")
        }

        override fun onResponse(call: Call<DiscoverResult>?, response: Response<DiscoverResult>) {
            if (response.isSuccessful) {
                val results = response.body()
                //println(results)
                results?.let { onSuccess(it) }
            }
        }

    })
}

fun movieDetails(service: TMDBService,
                 movieId: Int,
                 onSuccess: (results: MovieDetails) -> Unit) {

    service.movieDetails2(movieId).enqueue(object : Callback<MovieDetails> {
        override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
            if (response.isSuccessful) {
                val results = response.body()
                println(results)
                results?.let {
                    onSuccess(it)
                }
            }
        }

        override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
            TODO("not implemented")
        }

    })
}

interface TMDBService {

    @GET("discover/movie?language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false")
    fun discover(@Query("page") page: Int): Call<DiscoverResult>

    @GET("movie/{id}?language=en-US")
    fun movieDetails(@Path("id") id: Int): Call<MovieDetails>

    @GET("movie/{id}?language=en-US&append_to_response=credits")
    fun movieDetails2(@Path("id") id: Int): Call<MovieDetails>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): TMDBService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .addInterceptor(TmdbInterceptor())
                    .build()

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TMDBService::class.java)
        }
    }
}

class TmdbInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        val url = request.url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.TmdbApiKey)
                .build()

        request = request
                .newBuilder()
                .url(url)
                .build()

        return chain.proceed(request)
    }

}
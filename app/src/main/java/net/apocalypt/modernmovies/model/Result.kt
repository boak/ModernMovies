package net.apocalypt.modernmovies.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "results")
data class Result(@SerializedName("vote_count") var vote_count: Int?,
                  @PrimaryKey @SerializedName("id") var id: Int?,
                  @SerializedName("video") var video: Boolean?,
                  @SerializedName("vote_average") var voteAverage: Double?,
                  @SerializedName("title") var title: String?,
                  @SerializedName("popularity") var popularity: Double?,
                  @SerializedName("poster_path") var posterPath: String?,
                  @SerializedName("original_language") var originalLanguage: String?,
                  @SerializedName("original_title") var originalTitle: String?,
                  @TypeConverters(ListTypeConverters::class) @SerializedName("genre_ids") var genreIds: List<Int>?,
                  @SerializedName("backdrop_path") var backdropPath: String?,
                  @SerializedName("adult") var adult: Boolean?,
                  @SerializedName("overview") var overview: String?,
                  @SerializedName("release_date") var releaseDate: String?)


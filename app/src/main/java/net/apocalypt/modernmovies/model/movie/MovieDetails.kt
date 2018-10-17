package net.apocalypt.modernmovies.model.movie

import com.google.gson.annotations.SerializedName

data class MovieDetails(

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("imdb_id")
    val imdbId: String? = null,

    @SerializedName("video")
    val video: Boolean? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("revenue")
    val revenue: Int? = null,

    @SerializedName("credits")
    val credits: Credits? = null,

    @SerializedName("genres")
    val genres: List<GenresItem?>? = null,

    @SerializedName("popularity")
    val popularity: Double? = null,

    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountriesItem?>? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null,

    @SerializedName("budget")
    val budget: Int? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("runtime")
    val runtime: Int? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguagesItem?>? = null,

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompaniesItem?>? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,

    @SerializedName("tagline")
    val tagline: String? = null,

    @SerializedName("adult")
    val adult: Boolean? = null,

    @SerializedName("homepage")
    val homepage: String? = null,

    @SerializedName("status")
    val status: String? = null
)

data class BelongsToCollection(

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null
)

data class CastItem(

    @SerializedName("cast_id")
    val castId: Int? = null,

    @SerializedName("character")
    val character: String? = null,

    @SerializedName("gender")
    val gender: Int? = null,

    @SerializedName("credit_id")
    val creditId: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("profile_path")
    val profilePath: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("order")
    val order: Int? = null
)

data class Credits(

    @SerializedName("cast")
    val cast: List<CastItem?>? = null,

    @SerializedName("crew")
    val crew: List<CrewItem?>? = null
)

data class CrewItem(

    @SerializedName("gender")
    val gender: Int? = null,

    @SerializedName("credit_id")
    val creditId: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("profile_path")
    val profilePath: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("department")
    val department: String? = null,

    @SerializedName("job")
    val job: String? = null
)

data class GenresItem(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("id")
    val id: Int? = null
)

data class ProductionCompaniesItem(

    @SerializedName("logo_path")
    val logoPath: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("origin_country")
    val originCountry: String? = null
)

data class ProductionCountriesItem(

    @SerializedName("iso_3166_1")
    val iso31661: String? = null,

    @SerializedName("name")
    val name: String? = null
)

data class SpokenLanguagesItem(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("iso_639_1")
    val iso6391: String? = null
)

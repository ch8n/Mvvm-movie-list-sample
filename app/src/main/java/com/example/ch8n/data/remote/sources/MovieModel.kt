package com.example.ch8n.data.remote.sources

import com.google.gson.annotations.SerializedName

//----------------------------------Search Movie----------------------------------------

data class MovieSearchResponse(
    @SerializedName("Response")
    val response: String? = "",
    @SerializedName("Search")
    val search: List<MovieDetail>? = listOf(),
    @SerializedName("totalResults")
    val totalResults: String? = ""
)

data class MovieDetail(
    @SerializedName("imdbID")
    val imdbID: String? = "",
    @SerializedName("Poster")
    val poster: String? = "",
    @SerializedName("Title")
    val title: String? = "",
    @SerializedName("Type")
    val type: String? = "",
    @SerializedName("Year")
    val year: String? = ""
)

data class MovieSearchRequest(val query: String)

//----------------------------------Get Movie----------------------------------------

data class MovieDetailResponse(
    @SerializedName("Actors")
    val actors: String? = "",
    @SerializedName("Awards")
    val awards: String? = "",
    @SerializedName("BoxOffice")
    val boxOffice: String? = "",
    @SerializedName("Country")
    val country: String? = "",
    @SerializedName("DVD")
    val dVD: String? = "",
    @SerializedName("Director")
    val director: String? = "",
    @SerializedName("Genre")
    val genre: String? = "",
    @SerializedName("imdbID")
    val imdbID: String? = "",
    @SerializedName("imdbRating")
    val imdbRating: String? = "",
    @SerializedName("imdbVotes")
    val imdbVotes: String? = "",
    @SerializedName("Language")
    val language: String? = "",
    @SerializedName("Metascore")
    val metascore: String? = "",
    @SerializedName("Plot")
    val plot: String? = "",
    @SerializedName("Poster")
    val poster: String? = "",
    @SerializedName("Production")
    val production: String? = "",
    @SerializedName("Rated")
    val rated: String? = "",
    @SerializedName("Ratings")
    val ratings: List<Rating>? = listOf(),
    @SerializedName("Released")
    val released: String? = "",
    @SerializedName("Response")
    val response: String? = "",
    @SerializedName("Runtime")
    val runtime: String? = "",
    @SerializedName("Title")
    val title: String? = "",
    @SerializedName("Type")
    val type: String? = "",
    @SerializedName("Website")
    val website: String? = "",
    @SerializedName("Writer")
    val writer: String? = "",
    @SerializedName("Year")
    val year: String? = ""
)

data class Rating(
    @SerializedName("Source")
    val source: String? = "",
    @SerializedName("Value")
    val value: String? = ""
)

data class MovieDetailRequest(val movieId: String)

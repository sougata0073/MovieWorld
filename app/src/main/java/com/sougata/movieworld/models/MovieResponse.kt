package com.sougata.movieworld.models

import com.google.gson.annotations.SerializedName

data class MovieResponse (
    @SerializedName("page") var page: Int? = null,
    @SerializedName("next") var next: String? = null,
    @SerializedName("entries") var entries: Int? = null,
    @SerializedName("results") var results: List<Movie> = emptyList()
)
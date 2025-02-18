package com.sougata.movieworld.models

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("entries") var entries: Int? = null,
    @SerializedName("results") var results: List<String> = emptyList()
)

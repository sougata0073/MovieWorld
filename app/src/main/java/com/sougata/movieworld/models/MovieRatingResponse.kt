package com.sougata.movieworld.models

import com.google.gson.annotations.SerializedName

data class MovieRatingResponse(
    @SerializedName("results") val results: MovieRating
)

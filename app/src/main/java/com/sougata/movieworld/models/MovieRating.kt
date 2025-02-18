package com.sougata.movieworld.models

import com.google.gson.annotations.SerializedName

data class MovieRating(
    @SerializedName("tconst") var tconst: String?,
    @SerializedName("averageRating") var averageRating: Float?,
    @SerializedName("numVotes") var numVotes: Int?
)

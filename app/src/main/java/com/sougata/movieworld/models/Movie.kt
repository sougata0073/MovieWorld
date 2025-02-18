package com.sougata.movieworld.models

import com.google.gson.annotations.SerializedName


data class Movie(

    @SerializedName("_id") var id: String? = null,
    @SerializedName("id") var imdbId: String? = null,
    @SerializedName("primaryImage") var primaryImage: PrimaryImage? = null,
    @SerializedName("titleType") var titleType: TitleType? = null,
    @SerializedName("titleText") var titleText: TitleText? = null,
    @SerializedName("originalTitleText") var originalTitleText: TitleText? = null,
    @SerializedName("releaseYear") var releaseYear: ReleaseYear? = null,
    @SerializedName("releaseDate") var releaseDate: ReleaseDate? = null

)
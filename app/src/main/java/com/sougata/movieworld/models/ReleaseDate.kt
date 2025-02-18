package com.sougata.movieworld.models

import com.google.gson.annotations.SerializedName


data class ReleaseDate (

  @SerializedName("day") var day: Int? = null,
  @SerializedName("month") var month: Int? = null,
  @SerializedName("year") var year: Int? = null

)
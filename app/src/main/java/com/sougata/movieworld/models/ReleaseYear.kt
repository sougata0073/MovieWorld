package com.sougata.movieworld.models

import com.google.gson.annotations.SerializedName


data class ReleaseYear (

  @SerializedName("year") var year: Int? = null,
  @SerializedName("endYear") var endYear: Int? = null

)
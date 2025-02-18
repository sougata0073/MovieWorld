package com.sougata.movieworld.models

import com.google.gson.annotations.SerializedName


data class TitleType (

  @SerializedName("text") var text: String? = null,
  @SerializedName("id") var id: String? = null,
  @SerializedName("isSeries") var isSeries: Boolean? = null,
  @SerializedName("isEpisode") var isEpisode: Boolean? = null

)
package com.sougata.movieworld.models

import com.google.gson.annotations.SerializedName


data class OriginalTitleText (

  @SerializedName("text"       ) var text      : String? = null,
  @SerializedName("__typename" ) var _typename : String? = null

)
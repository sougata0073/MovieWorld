package com.sougata.movieworld.models

import com.google.gson.annotations.SerializedName


data class TitleText (

  @SerializedName("text") var text: String? = null

)
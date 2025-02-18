package com.sougata.movieworld.models

import com.google.gson.annotations.SerializedName


data class Caption (

  @SerializedName("plainText") var plainText: String? = null

)
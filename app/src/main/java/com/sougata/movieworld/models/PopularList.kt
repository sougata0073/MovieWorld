package com.sougata.movieworld.models

import androidx.lifecycle.MutableLiveData

data class PopularList(
    var listName: String,
    var label: String,
    var liveData: MutableLiveData<List<MyMovie>>
)
package com.sougata.movieworld.models

import androidx.room.Ignore

class MyMovie() {

    lateinit var name: String
    var year: Int = 0
    lateinit var type: String
    lateinit var description: String
    lateinit var imageUrl: String
    lateinit var imdbId: String

    @Ignore
    var isShimmer: Boolean = false // Room database will ignore this field due to the annotation

    constructor(movie: Movie) : this() {
        this.name = movie.originalTitleText?.text.toString()
        this.year = movie.releaseYear?.year ?: 0
        this.type = movie.titleType?.text.toString()
        this.description = movie.primaryImage?.caption?.plainText.toString()
        this.imageUrl = movie.primaryImage?.url.toString()
        this.imdbId = movie.imdbId.toString()
    }

    constructor(watchlistMovie: WatchlistMovie) : this() {
        this.name = watchlistMovie.name
        this.year = watchlistMovie.releaseYear
        this.type = watchlistMovie.type
        this.description = watchlistMovie.description
        this.imageUrl = watchlistMovie.posterUrl
        this.imdbId = watchlistMovie.imdbId
    }

    constructor(
        name: String,
        year: Int,
        type: String,
        description: String,
        imageUrl: String,
        imdbId: String
    ) : this() {
        this.name = name
        this.year = year
        this.type = type
        this.description = description
        this.imageUrl = imageUrl
        this.imdbId = imdbId
    }

    constructor(isShimmer: Boolean, imdbId: String) : this() {
        this.isShimmer = isShimmer
        this.imdbId = imdbId
    }

}
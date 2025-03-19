package com.sougata.movieworld.retrofit

import android.util.Log
import com.sougata.movieworld.models.MovieRating
import com.sougata.movieworld.models.MyMovie

class Repository(private val movieService: MovieService) {

    suspend fun getMoviesByGenre(genre: String): List<MyMovie> {

        Log.d("Retrofit", "API Call")

        val result =
            this.movieService.getMoviesByGenre(genre = genre).body()?.results?.map { MyMovie(it) }!!

        return result

    }

    suspend fun getMoviesByList(list: String): List<MyMovie> {
        Log.d("Retrofit", "API Call")

        val result =
            this.movieService.getMoviesByList(list = list).body()?.results?.map { MyMovie(it) }!!

        return result
    }

    suspend fun getMovieRatingByImdbId(imdbId: String): MovieRating {
        Log.d("RatingCall", "API Call")
        return this.movieService.getMovieRatingByImdbId(imdbId).body()?.results ?: MovieRating(
            imdbId,
            0F,
            0
        )
    }

    suspend fun getMoviesByName(name: String): List<MyMovie> {
        val movieResponse =
            this.movieService.getMoviesByName(name = name).body()?.results ?: emptyList()

        return movieResponse.map { MyMovie(it) }
    }


}
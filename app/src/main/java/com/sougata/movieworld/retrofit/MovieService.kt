package com.sougata.movieworld.retrofit

import com.sougata.movieworld.models.MovieRatingResponse
import com.sougata.movieworld.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("titles")
    suspend fun getMoviesByGenre(
        @Query("sort") sort: String = "year.decr",
        @Query("limit") movieLimit: Int = 10,
        @Query("genre") genre: String
    ): Response<MovieResponse>

    @GET("titles")
    suspend fun getMoviesByList(
        @Query("list") list: String,
        @Query("sort") sort: String = "year.decr",
        @Query("limit") movieLimit: Int = 10
    ): Response<MovieResponse>

    @GET("titles/{imdbId}/ratings")
    suspend fun getMovieRatingByImdbId(@Path("imdbId") imdbId: String): Response<MovieRatingResponse>

    @GET("titles/search/title/{name}")
    suspend fun getMoviesByName(
        @Path("name") name: String,
        @Query("sort") sort: String = "year.decr",
        @Query("exact") exact: String = "false"
    ): Response<MovieResponse>

}
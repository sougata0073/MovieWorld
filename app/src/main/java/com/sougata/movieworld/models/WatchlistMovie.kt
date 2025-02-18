package com.sougata.movieworld.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "watchlist_movie_details",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"], // Primary key of User table
            childColumns = ["user_id"], // Foreign key in WatchlistMovie table
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WatchlistMovie(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "release_year")
    var releaseYear: Int,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "rating")
    var rating: Int,

    @ColumnInfo(name = "rating_count")
    var ratingCount: Int,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "poster_url")
    var posterUrl: String,

    @ColumnInfo(name = "user_id")
    var userId: Int,

    @ColumnInfo(name = "imdb_id")
    var imdbId: String


)
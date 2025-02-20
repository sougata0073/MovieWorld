package com.sougata.movieworld.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(
    tableName = "user_liked_genre_details",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"], // Primary key of User table
            childColumns = ["user_id"], // Foreign key in WatchlistMovie table
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class Genre(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "user_id")
    var userId: Int

) {

    @Ignore
    var isSelected: Boolean = false

    @Ignore
    var toBeShownInProfile = false

    @Ignore
    constructor(name: String) : this(0, name, 0)
}
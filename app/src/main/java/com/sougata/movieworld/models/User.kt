package com.sougata.movieworld.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_details")
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "birthday")
    var birthday: String,

    @ColumnInfo(name = "country")
    var country: String,

    @ColumnInfo(name = "is_active")
    var isActive: Boolean
)

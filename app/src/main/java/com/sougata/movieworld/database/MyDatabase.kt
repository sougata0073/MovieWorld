package com.sougata.movieworld.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sougata.movieworld.models.SearchHistory
import com.sougata.movieworld.models.User
import com.sougata.movieworld.models.WatchlistMovie


@Database(
    entities = [User::class, WatchlistMovie::class, SearchHistory::class], version = 1
)
abstract class MyDatabase : RoomDatabase() {

    abstract val dao: DAO

    companion object {
        @Volatile
        private var DATABASE_INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {

                var instance: MyDatabase? = DATABASE_INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "users_database"
                    )
                        // Destroys old database if database schema changes without throwing error
                        // if not used but schema changes then room will give error
                        .fallbackToDestructiveMigration()
                        .build()
                }

                this.DATABASE_INSTANCE = instance

                return instance

            }
        }
    }

}
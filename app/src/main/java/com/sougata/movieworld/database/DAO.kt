package com.sougata.movieworld.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sougata.movieworld.models.Genre
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.models.SearchHistory
import com.sougata.movieworld.models.User
import com.sougata.movieworld.models.WatchlistMovie


@Dao
interface DAO {

    @Insert
    suspend fun insertUser(user: User)

    @Insert
    suspend fun insertWatchlistMovie(watchlistMovie: WatchlistMovie)

    @Insert
    suspend fun insertUserLikedGenre(genre: Genre)

    @Query(
        """
        insert into search_history (text, user_id) values
        (:text, (select id from user_details where is_active = 1 limit 1))
    """
    )
    suspend fun insertSearchHistory(text: String)

    @Query("select * from user_details")
    fun getAllUsers(): LiveData<List<User>>

    @Query(
        """
        select 
        name as name,
        release_year as year,
        type as type,
        description as description,
        poster_url as imageUrl,
        imdb_id as imdbId
        from 
        watchlist_movie_details as wmv
        where user_id = (
        select id from user_details where is_active = 1 limit 1
        )
        """
    )
    fun getAllWatchlistMovies(): LiveData<List<MyMovie>>

    @Query("select id from user_details where is_active = 1 limit 1")
    fun getActiveUserId(): Int

    @Query("delete from watchlist_movie_details where imdb_id = :imdbId")
    suspend fun deleteWatchlistMovie(imdbId: String)

    @Query(
        """select exists (
        select * from watchlist_movie_details 
        where imdb_id = :imdbId and 
        user_id = (
            select id from user_details where is_active = 1 limit 1
            )
        )"""
    )
    suspend fun getIsInWatchlist(imdbId: String): Boolean

    @Update
    suspend fun updateWatchList(watchlistMovie: WatchlistMovie)

    @Query("select id from watchlist_movie_details where imdb_id = :imdbId")
    fun getWatchlistMovieId(imdbId: String): Int

    @Query("select * from search_history order by id desc")
    fun getAllHistoryList(): LiveData<List<SearchHistory>>

    @Delete
    suspend fun deleteSearchHistory(searchHistory: SearchHistory)

    @Query("select count(id) from user_details")
    fun getUserCount(): Int

    @Query("update user_details set is_active = 0 where is_active = 1")
    suspend fun deactivateAllActiveUsers()

    @Query("select * from user_details where is_active = 1 limit 1")
    suspend fun getActiveUser(): User

    @Query(
        """
        select * from user_liked_genre_details
        where user_id = (select id from user_details where is_active = 1 limit 1)
        """
    )
    fun getActiveUserLikedGenreList(): LiveData<List<Genre>>

    @Update
    suspend fun updateUser(user: User)

    @Query("""
        update user_details
        set is_active = 0
        where id != :userId
    """)
    suspend fun deactivateUserExceptThisId(userId: Int)

    @Query("""
        update user_details
        set is_active = 1
        where id = :userId
    """)
    suspend fun makeActiveThisUser(userId: Int)

    @Query("""
        delete from user_liked_genre_details
        where user_id = :userId
    """)
    suspend fun deleteUserLikedGenresById(userId: Int)

    @Delete
    suspend fun deleteUser(user: User)
}
package com.sougata.movieworld.database

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import com.sougata.movieworld.models.Genre
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.models.SearchHistory
import com.sougata.movieworld.models.User
import com.sougata.movieworld.models.WatchlistMovie

class Repository(private val dao: DAO) {

    suspend fun insertUser(user: User) {
        this.dao.insertUser(user)
    }

    @Throws(SQLiteException::class)
    suspend fun insertWatchlistMovie(myMovie: MyMovie, activeUserId: Int) {

        val watchlistMovie = WatchlistMovie(
            0,
            myMovie.name,
            myMovie.year,
            myMovie.type,
            0,
            0,
            myMovie.description,
            myMovie.imageUrl,
            activeUserId,
            myMovie.imdbId
        )

        this.dao.insertWatchlistMovie(watchlistMovie)

    }

    suspend fun insertUserLikedGenre(genre: Genre) {
        this.dao.insertUserLikedGenre(genre)
    }

    fun getAllUsers(): LiveData<List<User>> {
        return this.dao.getAllUsers()
    }

    fun getAllWatchlistMovies(): LiveData<List<MyMovie>> {
        return this.dao.getAllWatchlistMovies()
    }

    fun getActiveUserId(): Int {
        return this.dao.getActiveUserId()
    }

    suspend fun deleteWatchlistMovie(imdbId: String) {
        this.dao.deleteWatchlistMovie(imdbId)
    }

    suspend fun getIsInWatchlist(imdbId: String): Boolean {
        return this.dao.getIsInWatchlist(imdbId)
    }

    suspend fun updateWatchList(watchlistMovie: WatchlistMovie) {
        this.dao.updateWatchList(watchlistMovie)
    }

    fun getWatchlistMovieId(imdbId: String): Int {
        return this.dao.getWatchlistMovieId(imdbId)
    }

    suspend fun insertSearchHistory(text: String) {
        this.dao.insertSearchHistory(text)
    }

    fun getAllHistoryList(): LiveData<List<SearchHistory>> {
        return this.dao.getAllHistoryList()
    }

    suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        this.dao.deleteSearchHistory(searchHistory)
    }

    fun getUserCount(): Int {
        return this.dao.getUserCount()
    }

    suspend fun deactivateAllActiveUsers() {
        this.dao.deactivateAllActiveUsers()
    }

    suspend fun getActiveUser(): User {
        return this.dao.getActiveUser()
    }

    fun getActiveUserLikedGenreList(): LiveData<List<Genre>> {
        return this.dao.getActiveUserLikedGenreList()
    }

    suspend fun updateUser(user: User) {
        this.dao.updateUser(user)
    }

    suspend fun deactivateUserExceptThisId(userId: Int) {
        this.dao.deactivateUserExceptThisId(userId)
    }

    suspend fun makeActiveThisUser(userId: Int) {
        this.dao.makeActiveThisUser(userId)
    }

    suspend fun deleteUserLikedGenresById(userId: Int) {
        this.dao.deleteUserLikedGenresById(userId)
    }

    suspend fun deleteUser(user: User) {
        this.dao.deleteUser(user)
    }
}
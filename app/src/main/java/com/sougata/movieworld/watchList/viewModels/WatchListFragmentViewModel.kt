package com.sougata.movieworld.watchList.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sougata.movieworld.MainActivity.Companion.FAKE_MOVIE_LIST
import com.sougata.movieworld.database.Repository
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.models.WatchlistMovie
import com.sougata.movieworld.util.InputUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchListFragmentViewModel(private val repository: Repository) : ViewModel() {

    val watchlistMoviesList = this.repository.getAllWatchlistMovies()

    val fakeMyMovieList = FAKE_MOVIE_LIST

    fun updateWatchList(first: MyMovie, second: MyMovie) {

        viewModelScope.launch(Dispatchers.IO) {

            val activeUserId = repository.getActiveUserId()

            val idFirst = repository.getWatchlistMovieId(first.imdbId)
            val idSecond = repository.getWatchlistMovieId(second.imdbId)

            Log.d("touch", "updated")

            val firstWatchlistMovie = WatchlistMovie(
                idFirst,
                second.name,
                second.year,
                second.type,
                0,
                0,
                second.description,
                second.imageUrl,
                activeUserId,
                second.imdbId
            )

            val secondWatchlistMovie = WatchlistMovie(
                idSecond,
                first.name,
                first.year,
                first.type,
                0,
                0,
                first.description,
                first.imageUrl,
                activeUserId,
                first.imdbId
            )
            repository.updateWatchList(firstWatchlistMovie)
            repository.updateWatchList(secondWatchlistMovie)
        }

    }

}
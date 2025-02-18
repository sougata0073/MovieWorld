package com.sougata.movieworld.anyMovieList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sougata.movieworld.MainActivity.Companion.FAKE_MOVIE_LIST
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.retrofit.Repository
import com.sougata.movieworld.util.InputUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnyMovieListViewModel(private val repository: Repository) : ViewModel() {

    val movieListByGenre = MutableLiveData<List<MyMovie>>()

    val fakeMyMovieList = FAKE_MOVIE_LIST

    val heading = MutableLiveData<String>()

    private var prevGenre = ""

    fun loadMoviesByGenre(genre: String) {

        heading.value = genre

        if (movieListByGenre.value == null || prevGenre != genre) {

            this.prevGenre = genre
            this.movieListByGenre.value = null

            viewModelScope.launch(Dispatchers.IO) {
                try {
                    movieListByGenre.postValue(repository.getMoviesByGenre(genre))

                    Log.d("Retrofit", "Loaded")

                } catch (e: Exception) {

                    Log.d("Retrofit", e.message.toString())
                }
            }
        }
    }

}
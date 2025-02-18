package com.sougata.movieworld.search.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.models.SearchHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.sougata.movieworld.database.Repository as RoomRepository
import com.sougata.movieworld.retrofit.Repository as RetrofitRepository

class SearchFragmentViewModel(
    private val retrofitRepository: RetrofitRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    val allSearchHistoryList = this.roomRepository.getAllHistoryList()

    val moviesListByName = MutableLiveData<List<MyMovie>>()

    fun loadMoviesByName(name: String) {

        if (this.moviesListByName.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {

                    moviesListByName.postValue(retrofitRepository.getMoviesByName(name))

                    Log.d("Retrofit", "Loaded")

                } catch (e: Exception) {

                    Log.d("Retrofit", e.message.toString())
                }
            }
        }
    }

    fun insertSearchHistory(text: String) {
        CoroutineScope(Dispatchers.IO).launch {
            roomRepository.insertSearchHistory(text)
        }
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) {
        CoroutineScope(Dispatchers.IO).launch {
            roomRepository.deleteSearchHistory(searchHistory)
        }
    }


}
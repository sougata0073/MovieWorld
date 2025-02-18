package com.sougata.movieworld.search.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sougata.movieworld.database.Repository as RoomRepository
import com.sougata.movieworld.retrofit.Repository as RetrofitRepository


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val retrofitRepository: RetrofitRepository,
    private val roomRepository: RoomRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(SearchFragmentViewModel::class.java) ->
                SearchFragmentViewModel(this.retrofitRepository, this.roomRepository) as T

            else -> throw IllegalArgumentException("ViewModel Not Found")

        }
    }
}
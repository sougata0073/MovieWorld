package com.sougata.movieworld.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sougata.movieworld.watchList.viewModels.WatchListFragmentViewModel


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {

            modelClass.isAssignableFrom(WatchListFragmentViewModel::class.java) ->
                WatchListFragmentViewModel(this.repository) as T

            else -> throw IllegalArgumentException("ViewModel Not Found")

        }


    }


}
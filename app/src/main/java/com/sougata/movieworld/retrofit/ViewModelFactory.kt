package com.sougata.movieworld.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sougata.movieworld.anyMovieList.AnyMovieListViewModel
import com.sougata.movieworld.home.viewModels.HomeFragmentViewModel


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {

            modelClass.isAssignableFrom(HomeFragmentViewModel::class.java) ->
                HomeFragmentViewModel(this.repository) as T

            modelClass.isAssignableFrom(AnyMovieListViewModel::class.java) ->
                AnyMovieListViewModel(this.repository) as T


            else -> throw IllegalArgumentException("ViewModel Not Found")

        }


    }



}
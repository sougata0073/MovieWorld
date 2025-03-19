package com.sougata.movieworld.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sougata.movieworld.profile.viewModels.AddEditUserFragmentViewModel
import com.sougata.movieworld.profile.viewModels.ProfileFragmentViewModel
import com.sougata.movieworld.profile.viewModels.SwitchUserFragmentViewModel
import com.sougata.movieworld.profile.viewModels.UserLikedAddEditGenreListFragmentViewModel
import com.sougata.movieworld.watchList.viewModels.WatchListFragmentViewModel


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {

            modelClass.isAssignableFrom(WatchListFragmentViewModel::class.java) ->
                WatchListFragmentViewModel(this.repository) as T

            modelClass.isAssignableFrom(UserLikedAddEditGenreListFragmentViewModel::class.java) ->
                UserLikedAddEditGenreListFragmentViewModel(this.repository) as T

            modelClass.isAssignableFrom(ProfileFragmentViewModel::class.java) ->
                ProfileFragmentViewModel(this.repository) as T

            modelClass.isAssignableFrom(AddEditUserFragmentViewModel::class.java) ->
                AddEditUserFragmentViewModel(this.repository) as T

            modelClass.isAssignableFrom(SwitchUserFragmentViewModel::class.java) ->
                SwitchUserFragmentViewModel(this.repository) as T



            else -> throw IllegalArgumentException("ViewModel Not Found")

        }


    }


}
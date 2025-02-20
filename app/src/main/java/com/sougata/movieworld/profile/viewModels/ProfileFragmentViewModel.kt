package com.sougata.movieworld.profile.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.sougata.movieworld.R
import com.sougata.movieworld.database.Repository
import com.sougata.movieworld.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(private val repository: Repository) : ViewModel() {

    val activeUser = MutableLiveData<User>()

    val userLikedGenreList = this.repository.getActiveUserLikedGenreList()

    fun loadActiveUser() {
        viewModelScope.launch(Dispatchers.IO) {

            activeUser.postValue(repository.getActiveUser())

        }
    }

    fun onAddUserClicked(view: View) {
        view.findNavController().navigate(R.id.action_profileFragment_to_addEditUserFragment)
    }


}
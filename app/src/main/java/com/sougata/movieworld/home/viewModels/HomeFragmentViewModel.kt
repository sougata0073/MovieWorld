package com.sougata.movieworld.home.viewModels

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.sougata.movieworld.MainActivity.Companion.FAKE_MOVIE_LIST
import com.sougata.movieworld.R
import com.sougata.movieworld.retrofit.Repository
import com.sougata.movieworld.util.InputUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragmentViewModel(private val repository: Repository) : ViewModel(), Observable {

    val listOfList = InputUtil.getAllListNames()

    val fakeMyMovieList = FAKE_MOVIE_LIST

    fun loadMoviesByList(index: Int) {
        synchronized(this) {
            val liveData = listOfList[index].liveData

            if (liveData.value == null) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {

                        liveData.postValue(repository.getMoviesByList(listOfList[index].listName))

                        Log.d("Retrofit", "Loaded")

                    } catch (e: Exception) {

                        Log.d("Retrofit", e.toString())

                    }
                }
            }
        }
    }

    fun onBrowseAllClick(view: View) {

        view.findNavController().navigate(R.id.action_homeFragment_to_genreListFragment)

    }

    fun onGenreButtonsClick(view: View) {

        val button = view as MaterialButton
        view.findNavController().navigate(
            R.id.action_homeFragment_to_anyMovieListFragment,
            Bundle().apply { putString("genre", button.text.toString()) }
        )

    }

    fun onSettingsClick(view: View) {

        view.findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)

    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}
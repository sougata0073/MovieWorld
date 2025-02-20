package com.sougata.movieworld.profile.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sougata.movieworld.database.Repository
import com.sougata.movieworld.models.Genre
import com.sougata.movieworld.models.User
import com.sougata.movieworld.util.InputUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserLikedAddEditGenreListFragmentViewModel(private val repository: Repository) : ViewModel() {

    val allGenreList =
        MutableLiveData(InputUtil.getAllGenresList())

    val selectedGenreList = arrayListOf<Genre>()

    val isUserAdded = MutableLiveData(false)

    fun insertUser(user: User) {

        CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main) {
                delay(200)
            }

            repository.deactivateAllUsers()

            repository.insertUser(user)

            val activeUserId = repository.getActiveUserId()

            for (genre in selectedGenreList) {
                repository.insertUserLikedGenre(
                    Genre(0, genre.name, activeUserId)
                )
            }

            isUserAdded.postValue(true)
        }

    }
}
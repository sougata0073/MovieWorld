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

    // For normal mode
    val allGenreList =
        MutableLiveData(InputUtil.getAllGenresList())

    val selectedGenreList = arrayListOf<Genre>()

    val isUserAdded = MutableLiveData(false)

    // For edit mode
    val activeUserLikedGenres = this.repository.getActiveUserLikedGenreList()

    fun insertUser(user: User) {

        CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main) {
                delay(200)
            }

            repository.deactivateAllActiveUsers()

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

    fun updateUserLikedGenreList() {
        CoroutineScope(Dispatchers.IO).launch {
            val activeUserId = repository.getActiveUser().id

            repository.deleteUserLikedGenresById(activeUserId)

            for (genre in selectedGenreList) {
                repository.insertUserLikedGenre(
                    Genre(0, genre.name, activeUserId)
                )
            }
        }
    }
}
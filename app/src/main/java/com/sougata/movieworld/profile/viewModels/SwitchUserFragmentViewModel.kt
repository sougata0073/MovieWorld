package com.sougata.movieworld.profile.viewModels

import androidx.lifecycle.ViewModel
import com.sougata.movieworld.database.Repository
import com.sougata.movieworld.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SwitchUserFragmentViewModel(private val repository: Repository) : ViewModel() {

    val usersList = this.repository.getAllUsers()

    fun updateActiveUser(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.makeActiveThisUser(userId)
            repository.deactivateUserExceptThisId(userId)
        }
    }

    fun deleteUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteUser(user)
        }
    }
}
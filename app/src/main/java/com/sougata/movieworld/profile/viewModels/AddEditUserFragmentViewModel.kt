package com.sougata.movieworld.profile.viewModels

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sougata.movieworld.R
import com.sougata.movieworld.database.Repository
import com.sougata.movieworld.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class AddEditUserFragmentViewModel(private val repository: Repository) : ViewModel(), Observable {

    @Bindable
    var name = MutableLiveData("")

    @Bindable
    var email = MutableLiveData("")

    @Bindable
    var country = MutableLiveData("")

    @Bindable
    var birthday = MutableLiveData("")


    fun onCalendarClicked(view: View) {

        val currentDate = Calendar.getInstance()

        DatePickerDialog(
            view.context,
            R.style.DatePickerDialogTheme,
            { _, year, month, dayOfMonth ->

                birthday.value = "%02d-%02d-$year".format(dayOfMonth, month + 1)

            },
            currentDate.get(Calendar.YEAR),
            Calendar.MONTH - 1,
            currentDate.get(Calendar.DAY_OF_MONTH)
        ).show()

    }


    fun onNextClicked(view: View) {

        val birthdayRegex = Regex("^\\d{2}-\\d{2}-\\d{4}$")
        val date = birthday.value

        this.name.value?.ifEmpty {
            MaterialAlertDialogBuilder(view.context)
                .setTitle("Warning")
                .setMessage("Name can't be empty")
                .setNeutralButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }.show()

            return

        }

        if (date?.matches(birthdayRegex) == false && date.isNotEmpty()) {
            MaterialAlertDialogBuilder(view.context)
                .setTitle("Warning")
                .setMessage("Wrong date format")
                .setNeutralButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }.show()

            return

        }

        val bundle = Bundle().apply {
            putString("name", name.value)
            putString("email", email.value)
            putString("country", country.value)
            putString("birthday", birthday.value)
        }

        view.findNavController()
            .navigate(R.id.action_addUserFragment_to_userLikedAddEditGenreListFragment, bundle)

    }

    fun updateUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateUser(user)
        }
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}
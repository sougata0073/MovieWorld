package com.sougata.movieworld.home.clickHandlers

import android.os.Bundle
import androidx.navigation.NavController
import com.sougata.movieworld.R

class GenreListClickHandler {

    fun onGenreClick(genre: String, navController: NavController) {
        navController.navigate(
            R.id.action_genreListFragment_to_anyMovieListFragment,
            Bundle().apply { putString("genre", genre) }
        )

    }

}
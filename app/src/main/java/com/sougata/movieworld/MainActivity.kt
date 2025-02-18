package com.sougata.movieworld

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sougata.movieworld.database.DAO
import com.sougata.movieworld.database.MyDatabase
import com.sougata.movieworld.database.Repository
import com.sougata.movieworld.databinding.ActivityMainBinding
import com.sougata.movieworld.models.MovieRating
import com.sougata.movieworld.models.User
import com.sougata.movieworld.util.InputUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dao: DAO

    private lateinit var repository: Repository

    companion object {

        val FAKE_MOVIE_LIST = InputUtil.getFakeMovieList()

        val MOVIE_RATINGS_MAP = HashMap<String, MovieRating>()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        this.binding.bottomNav.setupWithNavController(navController)


        this.dao = MyDatabase.getInstance(this).dao
        this.repository = Repository(this.dao)


        CoroutineScope(Dispatchers.IO).launch {
            if (repository.getUserCount() == 0) {
                repository.insertUser(User(0, "User", "N/A", "", "N/A", true))
            }
        }
    }

}

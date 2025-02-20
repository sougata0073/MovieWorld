package com.sougata.movieworld.util

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteException
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.snackbar.Snackbar
import com.sougata.movieworld.MainActivity.Companion.MOVIE_RATINGS_MAP
import com.sougata.movieworld.R
import com.sougata.movieworld.database.DAO
import com.sougata.movieworld.database.MyDatabase
import com.sougata.movieworld.databinding.MovieBottomsheetDialogBinding
import com.sougata.movieworld.models.MovieRating
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.retrofit.MovieService
import com.sougata.movieworld.retrofit.RetrofitInstance
import com.sougata.movieworld.settings.SettingsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.sougata.movieworld.database.Repository as RoomRepository
import com.sougata.movieworld.retrofit.Repository as RetrofitRepository

class MovieBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: MovieBottomsheetDialogBinding

    private lateinit var dao: DAO

    private lateinit var roomRepository: RoomRepository

    private lateinit var movieService: MovieService

    private lateinit var retrofitRepository: RetrofitRepository

    private var movieRating = MutableLiveData<MovieRating>()

    private lateinit var imageUrl: String
    private lateinit var name: String
    private var year: Int = 0
    private lateinit var type: String
    private lateinit var description: String
    private lateinit var imdbId: String

    private var userNotFoundFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments

        this.imageUrl = args?.getString("imageUrl").toString()
        this.name = args?.getString("name").toString()
        this.year = args?.getInt("year") ?: 0
        this.type = args?.getString("type").toString()
        this.description = args?.getString("description").orEmpty()
        this.imdbId = args?.getString("imdbId").toString()

        Log.d("FragmentBottomSheet", "onCreate")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater, R.layout.movie_bottomsheet_dialog, container, false
        )

        Log.d("FragmentBottomSheet", "onCreateView")

        return this.binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.movieService = RetrofitInstance.getInstance().create(MovieService::class.java)

        this.retrofitRepository = RetrofitRepository(this.movieService)

        this.dao = MyDatabase.getInstance(requireContext()).dao

        this.roomRepository = RoomRepository(this.dao)

        if (MOVIE_RATINGS_MAP.contains(imdbId)) {
            this.movieRating.postValue(MOVIE_RATINGS_MAP[imdbId])
        } else {
            CoroutineScope(Dispatchers.IO).launch {

                val rating = async { retrofitRepository.getMovieRatingByImdbId(imdbId) }

                // Now this 2 lines will wait until the API call finishes due to async{ }
                movieRating.postValue(rating.await())
                MOVIE_RATINGS_MAP[imdbId] = rating.await()
            }
        }

        Glide.with(requireContext())
            .load(this.imageUrl)
            .transform(CenterCrop(), RoundedCorners(30))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.shimmerLayout.startShimmer()
                    binding.shimmerLayout.visibility = View.VISIBLE
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {

                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE

                    return false
                }
            })
            .into(this.binding.posterIV)

        this.binding.movieNameAndYearLabel.text = "${this.name} (${this.year})"
        this.binding.typeLabel.text = this.type
        this.binding.descriptionTV.text = this.description

        this.movieRating.observe(viewLifecycleOwner) {

            if (it == null) return@observe

            this.binding.ratingTV.text =
                "${this.movieRating.value?.averageRating} / 10 from IMDb (${
                    InputUtil.ratingCountFormatter(this.movieRating.value?.numVotes ?: 0)
                })"

        }

        this.binding.addToWatchlistCB.apply {
            CoroutineScope(Dispatchers.Main).launch {
                val isInWatchlist =
                    withContext(Dispatchers.IO) { roomRepository.getIsInWatchlist(imdbId) }

                Log.d("database", "is in watchlist: $isInWatchlist")

                isChecked = isInWatchlist

                setOnCheckedChangeListener { checkBox, checkedState -> onCheckBoxClick(checkBox, checkedState) }
            }


            Log.d("FragmentBottomSheet", "onViewCreated")
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(myMovie: MyMovie) = MovieBottomSheetDialogFragment().apply {
            arguments = Bundle().apply {

                putString("imageUrl", myMovie.imageUrl)
                putString("name", myMovie.name)
                putInt("year", myMovie.year)
                putString("type", myMovie.type)
                putString("description", myMovie.description)
                putString("imdbId", myMovie.imdbId)

            }
        }
    }

    private fun onCheckBoxClick(checkBox: CompoundButton, checkedState: Boolean) {

        val alwaysWhite = ContextCompat.getColor(requireContext(), R.color.always_white)

        if (SettingsFragment.LOAD_FAKE_DATA.value == true) {
            return
        }
        if (checkedState) {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    withContext(Dispatchers.IO) {
                        roomRepository.insertWatchlistMovie(
                            MyMovie(name, year, type, description, imageUrl, imdbId),
                            roomRepository.getActiveUserId()
                        )
                    }

                    Snackbar.make(requireView(), "Added to watchlist", Snackbar.LENGTH_SHORT)
                        .setTextColor(alwaysWhite).show()

                } catch (e: SQLiteException) {
                    userNotFoundFlag = true
                    checkBox.isChecked = false

                    Snackbar.make(
                        requireView(),
                        "No user found, add a user to add movies to watchlist",
                        Snackbar.LENGTH_SHORT
                    ).setTextColor(alwaysWhite).show()

                }
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {

                if (!userNotFoundFlag) {

                    roomRepository.deleteWatchlistMovie(imdbId)

                    withContext(Dispatchers.Main) {

                        Snackbar.make(
                            requireView(),
                            "Removed from watchlist",
                            Snackbar.LENGTH_SHORT
                        ).setTextColor(alwaysWhite).show()
                    }
                }
                userNotFoundFlag = false
            }
        }
    }

}
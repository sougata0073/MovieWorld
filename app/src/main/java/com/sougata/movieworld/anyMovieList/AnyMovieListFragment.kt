package com.sougata.movieworld.anyMovieList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sougata.movieworld.R
import com.sougata.movieworld.databinding.FragmentAnyMovieListBinding
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.retrofit.MovieService
import com.sougata.movieworld.retrofit.Repository
import com.sougata.movieworld.retrofit.RetrofitInstance
import com.sougata.movieworld.retrofit.ViewModelFactory
import com.sougata.movieworld.settings.SettingsFragment
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class AnyMovieListFragment : Fragment() {

    private lateinit var genre: String

    private lateinit var binding: FragmentAnyMovieListBinding

    private lateinit var viewModel: AnyMovieListViewModel

    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var movieService: MovieService

    private lateinit var repository: Repository

    private lateinit var recyclerViewAdapter: AnyMovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments

        this.genre = args?.getString("genre").toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_any_movie_list, container, false
        )

        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.movieService = RetrofitInstance.getInstance().create(MovieService::class.java)

        this.repository = Repository(this.movieService)

        this.viewModelFactory = ViewModelFactory(this.repository)

        this.viewModel =
            ViewModelProvider.create(
                this,
                this.viewModelFactory
            )[AnyMovieListViewModel::class.java]

        this.recyclerViewAdapter = AnyMovieListAdapter(
            // imdbId passed for diff util if not passed it will give error
            List(10) { MyMovie(isShimmer = true, imdbId = "") },
            requireActivity().supportFragmentManager
        )

        this.binding.movieListRV.apply {

            layoutManager = GridLayoutManager(requireContext(), 2)

            adapter = ScaleInAnimationAdapter(recyclerViewAdapter).apply {
                setFirstOnly(false)
                setDuration(150)
            }
        }

        this.binding.backBtn.setOnClickListener{ requireActivity().onBackPressedDispatcher.onBackPressed() }

        this.viewModel.loadMoviesByGenre(this.genre)

        this.binding.viewModel = this.viewModel

        this.binding.lifecycleOwner = viewLifecycleOwner

        this.viewModel.movieListByGenre.observe(viewLifecycleOwner) {

            if (SettingsFragment.LOAD_FAKE_DATA.value == false) {
                if (it == null) {
                    recyclerViewAdapter.changeData(List(10) {
                        MyMovie(
                            isShimmer = true,
                            imdbId = ""
                        )
                    })
//                    Log.d("shimmer", "data changed to shimmer")
                } else {
                    recyclerViewAdapter.setData(it)
                }
            }
        }

        SettingsFragment.LOAD_FAKE_DATA.observe(viewLifecycleOwner) {
            if (it) { recyclerViewAdapter.changeData(this.viewModel.fakeMyMovieList) }
        }
    }
}
package com.sougata.movieworld.watchList.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.sougata.movieworld.R
import com.sougata.movieworld.anyMovieList.AnyMovieListAdapter
import com.sougata.movieworld.database.DAO
import com.sougata.movieworld.database.MyDatabase
import com.sougata.movieworld.database.Repository
import com.sougata.movieworld.database.ViewModelFactory
import com.sougata.movieworld.databinding.FragmentWatchListBinding
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.settings.SettingsFragment
import com.sougata.movieworld.util.RecyclerViewItemTouchHelper
import com.sougata.movieworld.watchList.viewModels.WatchListFragmentViewModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class WatchListFragment : Fragment() {

    private lateinit var binding: FragmentWatchListBinding

    private lateinit var dao: DAO

    private lateinit var repository: Repository

    private lateinit var viewModel: WatchListFragmentViewModel

    private lateinit var recyclerViewAdapter: AnyMovieListAdapter

    private var isItemMoved = MutableLiveData(false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d("FragmentWatchList", "onCreateView() called")

        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_watch_list, container, false)

        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.dao = MyDatabase.getInstance(requireContext()).dao

        this.repository = Repository(this.dao)

        this.viewModel = ViewModelProvider(
            this,
            ViewModelFactory(this.repository)
        )[WatchListFragmentViewModel::class.java]

        this.recyclerViewAdapter = AnyMovieListAdapter(
            // imdbId passed for diff util if not passed it will give error
            List(10) { MyMovie(isShimmer = true, imdbId = "") },
            requireActivity().supportFragmentManager,
            this.viewModel
        )

        this.binding.movieListRV.apply {

            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ScaleInAnimationAdapter(recyclerViewAdapter).apply {
                setFirstOnly(false)
                setDuration(150)
            }

        }

        ItemTouchHelper(RecyclerViewItemTouchHelper(this.recyclerViewAdapter)).attachToRecyclerView(
            this.binding.movieListRV
        )

        this.binding.lifecycleOwner = viewLifecycleOwner

        this.viewModel.watchlistMoviesList.observe(viewLifecycleOwner) {
            recyclerViewAdapter.setData(it)
        }

        SettingsFragment.LOAD_FAKE_DATA.observe(viewLifecycleOwner) {
            if (it) {
                recyclerViewAdapter.changeData(this.viewModel.fakeMyMovieList)
            }
        }

        Log.d("FragmentWatchList", "onViewCreated() called")

    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        Log.d("FragmentWatchList", "onAttach() called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentWatchList", "onCreate() called")
    }

    override fun onStart() {
        super.onStart()
        Log.d("FragmentWatchList", "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FragmentWatchList", "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FragmentWatchList", "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FragmentWatchList", "onStop() called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FragmentWatchList", "onDestroyView() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FragmentWatchList", "onDestroy() called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("FragmentWatchList", "onDetach() called")
//        FRAGMENTS_STACK.pop()
//        FRAGMENTS_STACK_LOG.value = FRAGMENTS_STACK.toString()
    }


}
package com.sougata.movieworld.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.sougata.movieworld.R
import com.sougata.movieworld.anyMovieList.AnyMovieListAdapter
import com.sougata.movieworld.database.DAO
import com.sougata.movieworld.database.MyDatabase
import com.sougata.movieworld.databinding.FragmentSearchBinding
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.models.SearchHistory
import com.sougata.movieworld.retrofit.MovieService
import com.sougata.movieworld.retrofit.RetrofitInstance
import com.sougata.movieworld.search.viewModels.SearchFragmentViewModel
import com.sougata.movieworld.search.viewModels.ViewModelFactory
import com.sougata.movieworld.database.Repository as RoomRepository
import com.sougata.movieworld.retrofit.Repository as RetrofitRepository

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var dao: DAO

    private lateinit var roomRepository: RoomRepository

    private lateinit var movieService: MovieService

    private lateinit var retrofitRepository: RetrofitRepository

    private lateinit var viewModel: SearchFragmentViewModel

    private lateinit var searchView: SearchView

    private lateinit var searchBar: SearchBar

    private lateinit var searchHistoryListAdapter: SearchSuggestionListAdapter

    private lateinit var mainContentListAdapter: AnyMovieListAdapter

    private var historyList = mutableSetOf<SearchHistory>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.dao = MyDatabase.getInstance(requireContext()).dao

        this.roomRepository = RoomRepository(this.dao)

        this.movieService = RetrofitInstance.getInstance().create(MovieService::class.java)

        this.retrofitRepository = RetrofitRepository(this.movieService)

        this.viewModel = ViewModelProvider.create(
            this,
            ViewModelFactory(this.retrofitRepository, this.roomRepository)
        )[SearchFragmentViewModel::class.java]

        this.searchHistoryListAdapter =
            SearchSuggestionListAdapter(emptyList(), this.viewModel, this.binding)
        this.mainContentListAdapter =
            AnyMovieListAdapter(emptyList(), requireActivity().supportFragmentManager)


        this.searchView = this.binding.searchView
        this.searchBar = this.binding.searchBar


        val black = ContextCompat.getColor(requireContext(), R.color.black)
        val grey = ContextCompat.getColor(requireContext(), R.color.grey)

        this.searchBar.textView.apply {
            textSize = 20f
            setTextColor(black)
            setHintTextColor(grey)
        }

        this.searchView.editText.apply {
            textSize = 20f
            setTextColor(black)
            setHintTextColor(grey)

            setOnEditorActionListener { v, actionId, event ->

                if (text.toString().isEmpty()) return@setOnEditorActionListener true

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.loadMoviesByName(text.toString())
                    searchBar.setText(text)
                    mainContentListAdapter.setData(
                        List(10) { MyMovie(isShimmer = true, imdbId = "") }
                    )
                    searchView.hide()
                    viewModel.insertSearchHistory(text.toString())

                    true
                } else {
                    false
                }
            }

            addTextChangedListener {
                searchHistoryListAdapter.setData(
                    historyList.filter { it.text.contains(text, true) }
                )
            }
        }

        this.binding.searchHistoryRV.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = searchHistoryListAdapter
        }

        this.binding.mainContentRV.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = mainContentListAdapter
        }

        this.viewModel.allSearchHistoryList.observe(viewLifecycleOwner) {
            this.searchHistoryListAdapter.setData(it)
            this.historyList = it.toMutableSet()
        }

        this.viewModel.moviesListByName.observe(viewLifecycleOwner) {
            if (it != null) {
                this.mainContentListAdapter.setData(it)
            }
        }
    }
}
package com.sougata.movieworld.home.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sougata.movieworld.R
import com.sougata.movieworld.databinding.FragmentHomeBinding
import com.sougata.movieworld.home.recyclerViewAdapters.AllPopularListWrapperAdapter
import com.sougata.movieworld.home.viewModels.HomeFragmentViewModel
import com.sougata.movieworld.retrofit.MovieService
import com.sougata.movieworld.retrofit.Repository
import com.sougata.movieworld.retrofit.RetrofitInstance
import com.sougata.movieworld.retrofit.ViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var movieService: MovieService

    private lateinit var repository: Repository

    private lateinit var viewModel: HomeFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        Log.d("FragmentHome", "onCreateView() called")

        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.movieService = RetrofitInstance.getInstance().create(MovieService::class.java)

        this.repository = Repository(this.movieService)

        this.viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(this.repository)
            )[HomeFragmentViewModel::class.java]


        this.binding.allPopularListRV.apply {
            Log.d("adapter", "null")
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = AllPopularListWrapperAdapter(
                viewModel,
                viewLifecycleOwner,
                requireActivity().supportFragmentManager
            )

        }

        this.binding.viewModel = this.viewModel

        this.binding.lifecycleOwner = viewLifecycleOwner

        Log.d("FragmentHome", "onViewCreated() called")

    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        Log.d("FragmentHome", "onAttach() called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentHome", "onCreate() called")
    }

    override fun onStart() {
        super.onStart()
        Log.d("FragmentHome", "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FragmentHome", "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FragmentHome", "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FragmentHome", "onStop() called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FragmentHome", "onDestroyView() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FragmentHome", "onDestroy() called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("FragmentHome", "onDetach() called")
    }
}
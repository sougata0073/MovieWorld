package com.sougata.movieworld.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sougata.movieworld.R
import com.sougata.movieworld.databinding.FragmentGenreListBinding
import com.sougata.movieworld.home.clickHandlers.GenreListClickHandler
import com.sougata.movieworld.home.recyclerViewAdapters.GenreListAdapter
import com.sougata.movieworld.util.InputUtil
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class GenreListFragment : Fragment() {

    private lateinit var binding: FragmentGenreListBinding

    private lateinit var clickHandler: GenreListClickHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        this.binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_genre_list, container, false
        )

        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.clickHandler = GenreListClickHandler()

        this.binding.genreListRV.apply {

            layoutManager = GridLayoutManager(requireContext(), 2)

            adapter =
                ScaleInAnimationAdapter(GenreListAdapter(InputUtil.getAllGenreNames())).apply {
                    setFirstOnly(false)
                    setDuration(150)
                }

        }

        this.binding.lifecycleOwner = viewLifecycleOwner

    }

}
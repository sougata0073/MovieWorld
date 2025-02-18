package com.sougata.movieworld.home.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sougata.movieworld.databinding.PopularListWrapperItemBinding
import com.sougata.movieworld.home.viewModels.HomeFragmentViewModel
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.models.PopularList
import com.sougata.movieworld.settings.SettingsFragment
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class AllPopularListWrapperAdapter(
    private val viewModel: HomeFragmentViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<AllPopularListWrapperAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: PopularListWrapperItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var childAdapter: PopularMovieListAdapter

        fun bind(popularList: PopularList) {

                binding.headingLabel.text = popularList.label

                childAdapter = PopularMovieListAdapter(
                    // imdbId passed for diff util if not passed it will give error
                    List(10) { MyMovie(isShimmer = true, imdbId = "") },
                    fragmentManager
                )

                binding.mostPopularMoviesRV.apply {

                    layoutManager = LinearLayoutManager(
                        binding.mostPopularMoviesRV.context, LinearLayoutManager.HORIZONTAL, false
                    )

                    adapter = ScaleInAnimationAdapter(childAdapter).apply {
                        setFirstOnly(false)
                        setDuration(80)
                    }
                }

            viewModel.loadMoviesByList(adapterPosition)

            popularList.liveData.observe(lifecycleOwner) { this.childAdapter.setData(it) }

            SettingsFragment.LOAD_FAKE_DATA.observe(lifecycleOwner) {
                if (it) {
                    childAdapter.changeData(viewModel.fakeMyMovieList)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = PopularListWrapperItemBinding.inflate(
            inflater, parent, false
        )

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.viewModel.listOfList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(this.viewModel.listOfList[position])
    }


}
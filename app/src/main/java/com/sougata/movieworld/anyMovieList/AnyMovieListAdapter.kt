package com.sougata.movieworld.anyMovieList

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sougata.movieworld.databinding.MovieListItemBinding
import com.sougata.movieworld.databinding.ShimmerMovieListItemBinding
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.util.ItemMoveListenerForRecyclerView
import com.sougata.movieworld.util.MovieBottomSheetDialogFragment
import com.sougata.movieworld.util.MyMovieTypeDiffUtil
import com.sougata.movieworld.watchList.viewModels.WatchListFragmentViewModel
import java.util.Collections

class AnyMovieListAdapter(
    private var itemsList: List<MyMovie>,
    private val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<AnyMovieListAdapter.MyViewHolder>(),
    ItemMoveListenerForRecyclerView {

    private val viewTypeShimmer: Int = 0

    private val viewTypeNormal: Int = 1

    private lateinit var watchListFragmentViewModel: WatchListFragmentViewModel

    constructor(
        itemsList: List<MyMovie>,
        fragmentManager: FragmentManager,
        watchListFragmentViewModel: WatchListFragmentViewModel
    ) : this(itemsList, fragmentManager) {
        this.watchListFragmentViewModel = watchListFragmentViewModel
    }

    inner class MyViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myMovie: MyMovie) {
            if (this.binding is MovieListItemBinding) {
                // this.binding is smart casted to MovieListItemBinding due to if block
                this.binding.nameTV.text = myMovie.name
                this.binding.descriptionTV.text = myMovie.description

                Glide.with(this.binding.posterIV.context)
                    .load(myMovie.imageUrl)
                    .transform(CenterCrop(), RoundedCorners(20))
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

                this.binding.parentLayout.setOnClickListener {
                    MovieBottomSheetDialogFragment.getInstance(myMovie)
                        .show(fragmentManager, "MovieBottomSheetDialogFragment")

                }
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (itemsList[position].isShimmer) viewTypeShimmer else viewTypeNormal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            viewTypeNormal -> {
                val binding = MovieListItemBinding.inflate(
                    inflater, parent, false
                )
                return MyViewHolder(binding)
            }

            viewTypeShimmer -> {
                val binding = ShimmerMovieListItemBinding.inflate(
                    inflater, parent, false
                )
                return MyViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return this.itemsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (this.itemsList.isNotEmpty()) {
            holder.bind(this.itemsList[position])
        }
    }

    fun setData(newItemsList: List<MyMovie>) {

        val diffUtil = MyMovieTypeDiffUtil(this.itemsList, newItemsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        this.itemsList = newItemsList
        diffResult.dispatchUpdatesTo(this)

    }

    fun changeData(newItemsList: List<MyMovie>) {
        val itemCount = this.itemsList.size
        this.itemsList = newItemsList
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(this.itemsList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        this.watchListFragmentViewModel.updateWatchList(
            this.itemsList[fromPosition],
            this.itemsList[toPosition]
        )
    }

}
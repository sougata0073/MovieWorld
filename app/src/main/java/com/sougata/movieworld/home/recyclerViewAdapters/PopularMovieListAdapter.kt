package com.sougata.movieworld.home.recyclerViewAdapters

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
import com.sougata.movieworld.R
import com.sougata.movieworld.databinding.HomeFragmentMovieListItemBinding
import com.sougata.movieworld.databinding.ShimmerHomeFragmentMovieListItemBinding
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.util.MovieBottomSheetDialogFragment
import com.sougata.movieworld.util.MyMovieTypeDiffUtil

class PopularMovieListAdapter(
    private var itemsList: List<MyMovie>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<PopularMovieListAdapter.MyViewHolder>() {

    private val VIEW_TYPE_SHIMMER = 0

    private val VIEW_TYPE_NORMAL = 1

    inner class MyViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myMovie: MyMovie) {

            if (this.binding is HomeFragmentMovieListItemBinding) {
                // this.binding is smart casted to HomeFragmentMovieListItemBinding due to if block
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

                this.binding.nameTV.text = myMovie.name
                this.binding.descriptionTV.text = myMovie.description

                this.binding.parentLayout.setOnClickListener {
                    MovieBottomSheetDialogFragment.getInstance(myMovie)
                        .show(fragmentManager, "MovieBottomSheetDialogFragment")
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (itemsList[position].isShimmer) VIEW_TYPE_SHIMMER else VIEW_TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val binding = HomeFragmentMovieListItemBinding.inflate(
                    inflater, parent, false
                )
                return MyViewHolder(binding)
            }

            VIEW_TYPE_SHIMMER -> {
                val binding = ShimmerHomeFragmentMovieListItemBinding.inflate(
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

}
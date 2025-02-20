package com.sougata.movieworld.profile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.sougata.movieworld.databinding.UserLikedAddEditGenreListItemBinding
import com.sougata.movieworld.databinding.UserLikedGenreListItemBinding
import com.sougata.movieworld.models.Genre
import com.sougata.movieworld.profile.viewModels.UserLikedAddEditGenreListFragmentViewModel

class UserLikedGenreListAdapter(
    private var itemsList: List<Genre>,
    private val viewModel: UserLikedAddEditGenreListFragmentViewModel?
) :
    RecyclerView.Adapter<UserLikedGenreListAdapter.MyViewHolder>() {

    constructor(itemsList: List<Genre>) : this(itemsList, null)

    private val viewTypeAddGenreList = 0
    private val viewTypeProfileGenreList = 1

    inner class MyViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {

            if (this.binding is UserLikedAddEditGenreListItemBinding) {
                this.binding.genreTV.text = genre.name

                this.binding.selectedCB.apply {
                    setOnCheckedChangeListener(null)
                    isChecked = genre.isSelected
                    setOnCheckedChangeListener { _, isChecked ->

                        val tempGenre = viewModel?.allGenreList?.value?.get(adapterPosition)

                        if (isChecked) {
                            tempGenre?.isSelected = true
                            viewModel?.selectedGenreList?.add(tempGenre ?: Genre(""))
                        } else {
                            tempGenre?.isSelected = false
                            viewModel?.selectedGenreList?.remove(tempGenre)
                        }

                    }
                }
            } else if (this.binding is UserLikedGenreListItemBinding) {
                this.binding.genreTV.text = genre.name
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (this.itemsList[position].toBeShownInProfile) viewTypeProfileGenreList else viewTypeAddGenreList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            viewTypeProfileGenreList -> {
                val binding = UserLikedGenreListItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
                return MyViewHolder(binding)
            }

            viewTypeAddGenreList -> {
                val binding = UserLikedAddEditGenreListItemBinding.inflate(
                    inflater,
                    parent,
                    false
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
        holder.bind(this.itemsList[position])
    }

    fun setData(newItemsList: List<Genre>) {
        val diffUtil = DiffUtil(itemsList, newItemsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        this.itemsList = newItemsList
        diffResult.dispatchUpdatesTo(this)
    }

}
package com.sougata.movieworld.profile.adapters

import androidx.recyclerview.widget.DiffUtil
import com.sougata.movieworld.models.Genre

class UserLikedAddEditGenreListTypeDiffUtil(
    private val oldItemsList: List<Genre>,
    private val newItemsList: List<Genre>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return this.oldItemsList.size
    }

    override fun getNewListSize(): Int {
        return this.newItemsList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return this.newItemsList[newItemPosition].id == this.oldItemsList[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            this.newItemsList[newItemPosition].id != this.oldItemsList[oldItemPosition].id -> false
            this.newItemsList[newItemPosition].name != this.oldItemsList[oldItemPosition].name -> false
            this.newItemsList[newItemPosition].isSelected != this.oldItemsList[oldItemPosition].isSelected -> false
            this.newItemsList[newItemPosition].userId != this.oldItemsList[oldItemPosition].userId -> false

            else -> true
        }
    }
}
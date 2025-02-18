package com.sougata.movieworld.util

import androidx.recyclerview.widget.DiffUtil
import com.sougata.movieworld.models.MyMovie

class MyMovieTypeDiffUtil(
    private val oldList: List<MyMovie>,
    private val newList: List<MyMovie>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return this.oldList.size
    }

    override fun getNewListSize(): Int {
        return this.newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return this.oldList[oldItemPosition].imdbId == this.newList[newItemPosition].imdbId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            this.oldList[oldItemPosition].name != this.newList[newItemPosition].name -> false
            this.oldList[oldItemPosition].year != this.newList[newItemPosition].year -> false
            this.oldList[oldItemPosition].type != this.newList[newItemPosition].type -> false
            this.oldList[oldItemPosition].description != this.newList[newItemPosition].description -> false
            this.oldList[oldItemPosition].imageUrl != this.newList[newItemPosition].imageUrl -> false
            this.oldList[oldItemPosition].imdbId != this.newList[newItemPosition].imdbId -> false

            else -> true
        }
    }

}

package com.sougata.movieworld.search.adapters

import androidx.recyclerview.widget.DiffUtil
import com.sougata.movieworld.models.SearchHistory

class DiffUtil(
    private val oldItemsList: List<SearchHistory>,
    private val newItemsList: List<SearchHistory>
) : DiffUtil.Callback(){
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
            this.newItemsList[newItemPosition].text != this.oldItemsList[oldItemPosition].text -> false
            this.newItemsList[newItemPosition].userId != this.oldItemsList[oldItemPosition].userId -> false

            else -> true
        }
    }
}
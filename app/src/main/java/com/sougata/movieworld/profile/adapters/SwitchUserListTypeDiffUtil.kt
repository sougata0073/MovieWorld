package com.sougata.movieworld.profile.adapters

import androidx.recyclerview.widget.DiffUtil
import com.sougata.movieworld.models.User

class SwitchUserListTypeDiffUtil(
    private val oldItemsList: List<User>,
    private val newItemsList: List<User>
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
            this.newItemsList[newItemPosition].email != this.oldItemsList[oldItemPosition].email -> false
            this.newItemsList[newItemPosition].birthday != this.oldItemsList[oldItemPosition].birthday -> false
            this.newItemsList[newItemPosition].country != this.oldItemsList[oldItemPosition].country -> false
            this.newItemsList[newItemPosition].isActive != this.oldItemsList[oldItemPosition].isActive -> false

            else -> true
        }
    }
}
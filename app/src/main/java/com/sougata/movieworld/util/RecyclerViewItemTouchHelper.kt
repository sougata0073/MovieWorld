package com.sougata.movieworld.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RecyclerViewItemTouchHelper(private val itemMoveListener: ItemMoveListenerForRecyclerView) :
    ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {

        val layoutManager = recyclerView.layoutManager

        val dragFlags = when (layoutManager) {

            is GridLayoutManager, is StaggeredGridLayoutManager -> {

                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

            }

            is LinearLayoutManager -> {

                if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN
                } else {
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                }

            }

            else -> 0
        }

        return makeMovementFlags(dragFlags, 0)
    }

    // It will be executed when an item moves
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {

        itemMoveListener.onItemMoved(
            viewHolder.adapterPosition,
            target.adapterPosition
        )

        return true

    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
}
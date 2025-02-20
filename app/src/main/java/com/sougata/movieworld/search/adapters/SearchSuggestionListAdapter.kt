package com.sougata.movieworld.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sougata.movieworld.databinding.FragmentSearchBinding
import com.sougata.movieworld.databinding.SearchSuggestionListItemBinding
import com.sougata.movieworld.models.SearchHistory
import com.sougata.movieworld.search.viewModels.SearchFragmentViewModel

class SearchSuggestionListAdapter(
    private var itemsList: List<SearchHistory>,
    private val viewModel: SearchFragmentViewModel,
    private val searchFragmentBinding: FragmentSearchBinding
) : RecyclerView.Adapter<SearchSuggestionListAdapter.MyViewHolder>() {


    inner class MyViewHolder(private val binding: SearchSuggestionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchHistory: SearchHistory) {

            this.binding.historyTV.text = searchHistory.text

            this.binding.historyTV.setOnClickListener{
                searchFragmentBinding.searchView.editText.setText(searchHistory.text)
            }

            this.binding.deleteBtn.setOnClickListener{
                viewModel.deleteSearchHistory(itemsList[adapterPosition])
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = SearchSuggestionListItemBinding.inflate(
            inflater, parent, false
        )

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.itemsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    fun setData(newItemsList: List<SearchHistory>) {
        val diffUtil = DiffUtil(itemsList, newItemsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        this.itemsList = newItemsList
        diffResult.dispatchUpdatesTo(this)
    }


}
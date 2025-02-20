package com.sougata.movieworld.home.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sougata.movieworld.R
import com.sougata.movieworld.databinding.GenreListItemBinding
import com.sougata.movieworld.home.clickHandlers.GenreListClickHandler
import com.sougata.movieworld.models.Genre

class GenreListAdapter(private val itemsList: List<Genre>) :
    RecyclerView.Adapter<GenreListAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: GenreListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {

            this.binding.genreLabel.text = genre.name

            this.binding.parentLayout.setOnClickListener {

                val clickHandler = GenreListClickHandler()

                clickHandler.onGenreClick(genre.name, this.binding.parentLayout.findNavController())


            }

            Glide.with(this.binding.backgroundIV.context)
                .load(R.drawable.action_bg)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(this.binding.backgroundIV)

            Glide.with(this.binding.genreIV.context)
                .load(R.drawable.action_genre)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(this.binding.genreIV)

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreListAdapter.MyViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding: GenreListItemBinding =
            GenreListItemBinding.inflate(
                inflater, parent, false
            )

        return MyViewHolder(binding)

    }

    override fun getItemCount(): Int {
        if (this.itemsList.isEmpty()) {
            return 20
        }

        return this.itemsList.size
    }


    override fun onBindViewHolder(holder: GenreListAdapter.MyViewHolder, position: Int) {

        if (this.itemsList.isEmpty()) {

            holder.bind(Genre("Loading"))
            return
        }


        holder.bind(this.itemsList[position])
    }
}
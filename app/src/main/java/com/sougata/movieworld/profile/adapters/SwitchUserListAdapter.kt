package com.sougata.movieworld.profile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sougata.movieworld.R
import com.sougata.movieworld.databinding.SwitchUsersListItemBinding
import com.sougata.movieworld.models.User
import com.sougata.movieworld.profile.viewModels.SwitchUserFragmentViewModel
import com.sougata.movieworld.util.InputUtil

class SwitchUserListAdapter(
    private var itemsList: List<User>,
    private val viewModel: SwitchUserFragmentViewModel
) :
    RecyclerView.Adapter<SwitchUserListAdapter.MyViewHolder>() {


    inner class MyViewHolder(private val binding: SwitchUsersListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {

            Glide.with(this.binding.profileIV.context)
                .load(InputUtil.getRandomImageUrl(100, 100))
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(this.binding.profileIV)

            this.binding.nameTV.text = user.name
            this.binding.emailTV.text = user.email

            this.binding.parentLayout.setOnClickListener {
                viewModel.updateActiveUser(user.id)
                this.binding.parentLayout.findNavController().popBackStack()
            }

            this.binding.deleteBtn.setOnClickListener {

                if (user.isActive) {
                    MaterialAlertDialogBuilder(it.context)
                        .setTitle("Warning")
                        .setMessage("You cannot delete the active user")
                        .setPositiveButton("Ok") { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                } else {
                    MaterialAlertDialogBuilder(it.context)
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to delete this user?")
                        .setPositiveButton("Yes") { dialog, _ ->
                            viewModel.deleteUser(itemsList[adapterPosition])
                            dialog.dismiss()
                        }
                        .setNeutralButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<SwitchUsersListItemBinding>(
            inflater,
            R.layout.switch_users_list_item,
            parent,
            false
        )

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.itemsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(this.itemsList[position])
    }

    fun setData(newItemsList: List<User>) {
        val switchUserListTypeDiffUtil =
            SwitchUserListTypeDiffUtil(itemsList, newItemsList)
        val diffResult = DiffUtil.calculateDiff(switchUserListTypeDiffUtil)
        this.itemsList = newItemsList
        diffResult.dispatchUpdatesTo(this)
    }
}
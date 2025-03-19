package com.sougata.movieworld.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sougata.movieworld.R
import com.sougata.movieworld.database.DAO
import com.sougata.movieworld.database.MyDatabase
import com.sougata.movieworld.database.Repository
import com.sougata.movieworld.database.ViewModelFactory
import com.sougata.movieworld.databinding.FragmentSwitchUserBinding
import com.sougata.movieworld.profile.adapters.SwitchUserListAdapter
import com.sougata.movieworld.profile.viewModels.SwitchUserFragmentViewModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class SwitchUserFragment : Fragment() {

    private lateinit var binding: FragmentSwitchUserBinding

    private lateinit var dao: DAO

    private lateinit var repository: Repository

    private lateinit var viewModel: SwitchUserFragmentViewModel

    private lateinit var recyclerViewAdapter: SwitchUserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_switch_user, container, false)

        return this.binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.dao = MyDatabase.getInstance(requireContext()).dao

        this.repository = Repository(this.dao)

        this.viewModel = ViewModelProvider(
            this,
            ViewModelFactory(this.repository)
        )[SwitchUserFragmentViewModel::class.java]

        this.binding.lifecycleOwner = this

        this.recyclerViewAdapter =
            SwitchUserListAdapter(this.viewModel.usersList.value ?: emptyList(), this.viewModel)

        this.binding.usersListRV.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ScaleInAnimationAdapter(recyclerViewAdapter).apply {
                setFirstOnly(false)
                setDuration(150)
            }
        }

        this.viewModel.usersList.observe(this.viewLifecycleOwner) {
            this.recyclerViewAdapter.setData(it)
        }

    }

}
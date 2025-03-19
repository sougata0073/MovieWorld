package com.sougata.movieworld.profile.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.sougata.movieworld.R
import com.sougata.movieworld.database.DAO
import com.sougata.movieworld.database.MyDatabase
import com.sougata.movieworld.database.Repository
import com.sougata.movieworld.database.ViewModelFactory
import com.sougata.movieworld.databinding.FragmentProfileBinding
import com.sougata.movieworld.profile.adapters.UserLikedAddEditGenreListAdapter
import com.sougata.movieworld.profile.viewModels.ProfileFragmentViewModel
import com.sougata.movieworld.util.InputUtil
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel: ProfileFragmentViewModel

    private lateinit var dao: DAO

    private lateinit var repository: Repository

    private lateinit var interestedGenreListAdapter: UserLikedAddEditGenreListAdapter

    private var id: Int = 1
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var country: String
    private lateinit var birthday: String
    private var isActive: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d("FragmentProfile", "onCreateView() called")

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.dao = MyDatabase.getInstance(requireContext()).dao

        this.repository = Repository(this.dao)

        this.viewModel = ViewModelProvider(
            this,
            ViewModelFactory(this.repository)
        )[ProfileFragmentViewModel::class.java]

        this.interestedGenreListAdapter = UserLikedAddEditGenreListAdapter(emptyList())

        this.binding.interestedGenreRV.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
            adapter = ScaleInAnimationAdapter(interestedGenreListAdapter).apply {
                setFirstOnly(false)
                setDuration(150)
            }
        }

        this.viewModel.loadActiveUser()


        this.binding.viewModel = this.viewModel

        Glide.with(requireContext())
            .load(InputUtil.getRandomImageUrl(500, 500))
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .into(this.binding.profileIV)

        this.viewModel.activeUser.observe(this.viewLifecycleOwner) {

            this.id = it.id
            this.name = it.name
            this.email = it.email
            this.country = it.country
            this.birthday = it.birthday
            this.isActive = it.isActive

            this.binding.apply {
                nameTV.text = name
                emailTV.text = email
                countryTV.text = country
                birthdayTV.text = birthday
            }

            this.binding.editBtn.setOnClickListener {
                findNavController().navigate(
                    R.id.action_profileFragment_to_addEditUserFragment,
                    Bundle().apply {
                        putInt("id", id)
                        putString("name", name)
                        putString("email", email)
                        putString("country", country)
                        putString("birthday", birthday)
                        putBoolean("isActive", isActive)
                        putBoolean("isEdit", true)
                    })
            }
        }

        this.viewModel.userLikedGenreList.observe(this.viewLifecycleOwner) {
            it.forEach { genre -> genre.toBeShownInProfile = true }

            this.interestedGenreListAdapter.setData(it)
        }

        Log.d("FragmentProfile", "onViewCreated() called")
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        Log.d("FragmentProfile", "onAttach() called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentProfile", "onCreate() called")
    }

    override fun onStart() {
        super.onStart()
        Log.d("FragmentProfile", "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FragmentProfile", "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FragmentProfile", "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FragmentProfile", "onStop() called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FragmentProfile", "onDestroyView() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FragmentProfile", "onDestroy() called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("FragmentProfile", "onDetach() called")
    }

}
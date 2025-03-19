package com.sougata.movieworld.profile.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sougata.movieworld.R
import com.sougata.movieworld.database.DAO
import com.sougata.movieworld.database.MyDatabase
import com.sougata.movieworld.database.Repository
import com.sougata.movieworld.database.ViewModelFactory
import com.sougata.movieworld.databinding.FragmentUserLikedAddEditGenreListBinding
import com.sougata.movieworld.models.User
import com.sougata.movieworld.profile.adapters.UserLikedAddEditGenreListAdapter
import com.sougata.movieworld.profile.viewModels.UserLikedAddEditGenreListFragmentViewModel
import com.sougata.movieworld.util.InputUtil
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserLikedAddEditGenreListFragment : Fragment() {

    private lateinit var binding: FragmentUserLikedAddEditGenreListBinding

    private lateinit var dao: DAO

    private lateinit var repository: Repository

    private lateinit var viewModel: UserLikedAddEditGenreListFragmentViewModel

    private lateinit var genreListAdapter: UserLikedAddEditGenreListAdapter

    // If user navigated from add profile then this variables are needed
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var birthday: String
    private lateinit var country: String

    // If user navigated from add genre then this variables are needed
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments

        this.name = args?.getString("name").toString()
        this.email = args?.getString("email").toString()
        this.birthday = args?.getString("birthday").toString()
        this.country = args?.getString("country").toString()

        this.isEdit = args?.getBoolean("isEdit") ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.binding = FragmentUserLikedAddEditGenreListBinding.inflate(inflater, container, false)

        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.dao = MyDatabase.getInstance(requireContext()).dao

        this.repository = Repository(this.dao)

        this.binding.lifecycleOwner = this

        this.viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(this.repository)
            )[UserLikedAddEditGenreListFragmentViewModel::class.java]

        if (isEdit) {
            setUpForEditMode()
        } else {
            setUpForNormalMode()
        }

    }

    private fun setUpForNormalMode() {
        this.genreListAdapter =
            UserLikedAddEditGenreListAdapter(
                this.viewModel.allGenreList.value ?: emptyList(),
                this.viewModel
            )

        this.binding.genreListRV.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ScaleInAnimationAdapter(genreListAdapter).apply {
                setFirstOnly(false)
                setDuration(150)
            }
        }

        this.binding.finishBtn.setOnClickListener {
            this.viewModel.insertUser(
                User(0, this.name, this.email, this.birthday, this.country, true)
            )

            Snackbar.make(
                requireView(),
                "User added successfully",
                Snackbar.LENGTH_SHORT
            ).setTextColor(ContextCompat.getColor(requireContext(), R.color.always_white)).show()

        }

        this.viewModel.isUserAdded.observe(this.viewLifecycleOwner) {
            if (it) {
                findNavController().apply {
                    popBackStack()
                    popBackStack()
                }
            }
        }
    }

    private fun setUpForEditMode() {
        this.genreListAdapter = UserLikedAddEditGenreListAdapter(
            this.viewModel.activeUserLikedGenres.value ?: emptyList(),
            this.viewModel
        )

        this.binding.genreListRV.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ScaleInAnimationAdapter(genreListAdapter).apply {
                setFirstOnly(false)
                setDuration(150)
            }
        }

        this.binding.finishBtn.apply {
            text = "Save"
            setOnClickListener {

                viewModel.updateUserLikedGenreList()

                Snackbar.make(
                    requireView(),
                    "User updated successfully",
                    Snackbar.LENGTH_SHORT
                ).setTextColor(ContextCompat.getColor(requireContext(), R.color.always_white))
                    .show()

                findNavController().popBackStack()
            }
        }

        this.viewModel.activeUserLikedGenres.observe(this.viewLifecycleOwner) {

            CoroutineScope(Dispatchers.Main).launch {

                // makes a map from a list and decides key as written in the scope function
                val previouslySelectedGenres = it.associateBy { it.name }

//                Log.d("tag2", previouslySelectedGenres.toString())

                val allGenres = InputUtil.getAllGenresList()

                for (item in allGenres) {
                    if (previouslySelectedGenres.contains(item.name)) {
                        item.isSelected = true
//                        Log.d("tag2", "yes")
                    }
                }

                genreListAdapter.setData(allGenres)

            }
        }
    }
}

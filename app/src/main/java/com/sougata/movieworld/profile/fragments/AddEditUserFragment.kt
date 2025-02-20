package com.sougata.movieworld.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sougata.movieworld.R
import com.sougata.movieworld.database.DAO
import com.sougata.movieworld.database.MyDatabase
import com.sougata.movieworld.database.Repository
import com.sougata.movieworld.database.ViewModelFactory
import com.sougata.movieworld.databinding.FragmentAddEditUserBinding
import com.sougata.movieworld.models.User
import com.sougata.movieworld.profile.viewModels.AddEditUserFragmentViewModel

class AddEditUserFragment : Fragment() {

    private lateinit var binding: FragmentAddEditUserBinding

    private lateinit var viewModel: AddEditUserFragmentViewModel

    private lateinit var dao: DAO

    private lateinit var repository: Repository

    private var id: Int = 1
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var country: String
    private lateinit var birthday: String
    private var isActive: Boolean = true

    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.id = arguments?.getInt("id") ?: 1
        this.name = arguments?.getString("name").orEmpty()
        this.email = arguments?.getString("email").orEmpty()
        this.country = arguments?.getString("country").orEmpty()
        this.birthday = arguments?.getString("birthday").orEmpty()
        this.isActive = arguments?.getBoolean("isActive") ?: true

        this.isEdit = arguments?.getBoolean("isEdit") ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_user, container, false)

        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.dao = MyDatabase.getInstance(requireContext()).dao

        this.repository = Repository(this.dao)

        this.viewModel = ViewModelProvider(
            this,
            ViewModelFactory(this.repository)
        )[AddEditUserFragmentViewModel::class.java]

        this.binding.viewModel = viewModel

        this.binding.lifecycleOwner = this

        if (this.isEdit) {
            this.viewModel.name.value = this.name
            this.viewModel.email.value = this.email
            this.viewModel.country.value = this.country
            this.viewModel.birthday.value = this.birthday
            this.binding.nextBtn.apply {
                text = "Save"
                setOnClickListener {
                    viewModel.updateUser(
                        User(
                            this@AddEditUserFragment.id,
                            viewModel.name.value ?: "",
                            viewModel.email.value ?: "",
                            viewModel.birthday.value ?: "",
                            viewModel.country.value ?: "",
                            this@AddEditUserFragment.isActive
                        )
                    )

                    Snackbar.make(
                        requireView(),
                        "Profile updated successfully",
                        Snackbar.LENGTH_SHORT
                    ).setTextColor(ContextCompat.getColor(requireContext(), R.color.always_white))
                        .show()

                    findNavController().popBackStack()

                }
            }
        } else {
            this.binding.nextBtn.apply {
                text = "Next"
                setOnClickListener {
                    viewModel.onNextClicked(it)
                }
            }
        }
    }
}
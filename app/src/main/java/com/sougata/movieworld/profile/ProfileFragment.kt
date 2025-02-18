package com.sougata.movieworld.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.sougata.movieworld.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("FragmentProfile", "onCreateView() called")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val addUserBtn = view.findViewById<MaterialButton>(R.id.addUserBtn)

        addUserBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_addUserFragment)
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
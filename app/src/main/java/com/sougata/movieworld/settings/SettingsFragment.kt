package com.sougata.movieworld.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.materialswitch.MaterialSwitch
import com.sougata.movieworld.R

class SettingsFragment : Fragment() {

    companion object {

        val LOAD_FAKE_DATA = MutableLiveData(false)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fakeDataSwitch = view.findViewById<MaterialSwitch>(R.id.fakeDataSwitch)

        fakeDataSwitch.isChecked = LOAD_FAKE_DATA.value ?: false

        fakeDataSwitch.setOnCheckedChangeListener { _, isChecked ->
            LOAD_FAKE_DATA.value = isChecked
        }


    }
}
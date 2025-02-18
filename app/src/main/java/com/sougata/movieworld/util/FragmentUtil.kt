package com.sougata.movieworld.util

import androidx.navigation.NavOptions
import com.sougata.movieworld.R

object FragmentUtil {


    fun getFragmentAnimations() = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()


}
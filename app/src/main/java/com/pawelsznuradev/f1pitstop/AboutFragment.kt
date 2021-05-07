package com.pawelsznuradev.f1pitstop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Pawel Sznura on 23/04/2021.
 */

/**
 * About fragment
 * simple fragment just to inflate the layout
 *
 * @constructor Create empty About fragment
 */
class AboutFragment : Fragment() {


    override fun onResume() {
        super.onResume()
        activity?.title = "About"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }


}
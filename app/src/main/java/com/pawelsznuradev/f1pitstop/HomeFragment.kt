package com.pawelsznuradev.f1pitstop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.pawelsznuradev.f1pitstop.databinding.FragmentHomeBinding


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root

        // https://classroom.udacity.com/courses/ud9012/lessons/7466f670-3d47-4b60-8f6a-0914ce58f9ad/concepts/cda040dc-4139-4027-b8ee-d8119d451291

    }


}
package com.pawelsznuradev.f1pitstop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.pawelsznuradev.f1pitstop.databinding.FragmentResultBinding


private const val seasonKey = "season"
private const val raceNameKey = "raceName"
private const val driver1NameKey = "driver1Name"
private const val driver2NameKey = "driver2Name"
private const val driver1PitStopsKey = "driver1PitStop"
private const val driver2PitStopsKey = "driver2PitStop"


class ResultFragment : Fragment() {
    private var season: String? = null
    private var raceName: String? = null
    private var driver1Name: String? = null
    private var driver2Name: String? = null
    lateinit var driver1PitStops: DriverPitStops
    lateinit var driver2PitStops: DriverPitStops

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            season = it.getString(seasonKey)
            raceName = it.getString(raceNameKey)
            driver1Name = it.getString(driver1NameKey)
            driver2Name = it.getString(driver2NameKey)
            driver1PitStops = it.getParcelable(driver1PitStopsKey)!!
            driver2PitStops = it.getParcelable(driver2PitStopsKey)!!
        }
        activity?.title = "$season $raceName"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentResultBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)


        return binding.root
    }


//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ResultFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ResultFragment().apply {
//                arguments = Bundle().apply {
//                    putString(com.pawelsznuradev.f1pitstop.seasonKey, param1)
//                    putString(com.pawelsznuradev.f1pitstop.raceNameKey, param2)
//                }
//            }
//    }
}
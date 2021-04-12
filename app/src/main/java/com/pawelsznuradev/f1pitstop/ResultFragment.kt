package com.pawelsznuradev.f1pitstop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.pawelsznuradev.f1pitstop.databinding.FragmentResultBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val seasonKey = "season"
private const val raceNameKey = "raceName"
private const val driver1PitStopsKey = "driver1PitStop"
private const val driver2PitStopsKey = "driver2PitStop"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var season: String? = null
    private var raceName: String? = null
    lateinit var driver1PitStops: DriverPitStops
    lateinit var driver2PitStops: DriverPitStops

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            season = it.getString(seasonKey)
            raceName = it.getString(com.pawelsznuradev.f1pitstop.raceNameKey)
            driver1PitStops = it.getParcelable(driver1PitStopsKey)!!
            driver2PitStops = it.getParcelable(driver2PitStopsKey)!!
        }
        activity?.title = "$season $raceName"
        Log.e("onCreate", "driver1 = ${driver1PitStops.driverId}, driver2 = ${driver2PitStops.driverId}")
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putString(com.pawelsznuradev.f1pitstop.seasonKey, param1)
                    putString(com.pawelsznuradev.f1pitstop.raceNameKey, param2)
                }
            }
    }
}
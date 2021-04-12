package com.pawelsznuradev.f1pitstop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import com.pawelsznuradev.f1pitstop.databinding.FragmentHomeBinding
import com.pawelsznuradev.f1pitstop.network.ErgastApi
import com.pawelsznuradev.f1pitstop.network.ResponseDrivers
import com.pawelsznuradev.f1pitstop.network.ResponsePitStops
import com.pawelsznuradev.f1pitstop.network.ResponseRaces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    lateinit var races: IdNameCollection
    lateinit var drivers: IdNameCollection
    lateinit var driver1PitStops: DriverPitStops
    lateinit var driver2PitStops: DriverPitStops

    private lateinit var season: String
    private lateinit var round: String
    private lateinit var driverId1: String
    private lateinit var driverId2: String
    val bundle = Bundle()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        populateSeasons()

        binding.buttonCompare.setOnClickListener {

            Log.e(
                "All data",
                "season $season, round $round, driver1 $driverId1, driver2 $driverId2"
            )
            Log.e("PitStop1", driver1PitStops.toString())
            Log.e("PitStop2", driver2PitStops.toString())

        }
        return binding.root
    }



    private fun populateDrivers() {
        val raceNames = ArrayList(drivers.nameList)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, raceNames)
        (binding.selectDriver1.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.selectDriver1List.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l -> onDriver1Selected() }

        (binding.selectDriver2.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.selectDriver2List.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l -> onDriver2Selected() }
    }

    private fun onDriver1Selected() {
        driverId1 = drivers.getIdByName(binding.selectDriver1.editText?.text.toString())
        getPitStops(season, round, driverId1)

    }

    private fun onDriver2Selected() {
        driverId2 = drivers.getIdByName(binding.selectDriver2.editText?.text.toString())
        getPitStops(season, round, driverId2)
    }

    private fun populateRaces() {
        val raceNames = ArrayList(races.nameList)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, raceNames)
        (binding.selectRace.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.selectRaceList.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l -> onRaceSelected() }
    }

    private fun populateSeasons() {
        val listSeasons = mutableListOf<String>()
        for (i in 2011..Calendar.getInstance().get(Calendar.YEAR)) {
            listSeasons.add("$i")
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, listSeasons)
        (binding.selectSeason.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.selectSeasonList.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                onSeasonSelected()
            }
    }

    private fun onRaceSelected() {
        round = races.getIdByName(binding.selectRace.editText?.text.toString())
        getDrivers(season, round)
    }

    private fun onSeasonSelected() {
        season = binding.selectSeason.editText?.text.toString()
        getRaces(season)
    }


    private fun getPitStops(season: String, round: String, driverId: String) {
        ErgastApi.retrofitService.getPitStops(season, round, driverId)
            .enqueue(object : Callback<ResponsePitStops> {
                override fun onResponse(
                    call: Call<ResponsePitStops>,
                    response: Response<ResponsePitStops>
                ) {
                    if (driverId == driverId1) {
                        driver1PitStops =
                            response.body()!!.MRDataPitStops.RaceTable2.Races2[0].getDriverPitStops()
                    } else {
                        driver2PitStops =
                            response.body()!!.MRDataPitStops.RaceTable2.Races2[0].getDriverPitStops()
                    }
                }

                override fun onFailure(call: Call<ResponsePitStops>, t: Throwable) {
                    t.message?.let { Log.e("pitStopFAILURE", it) }
                }
            })
    }

    private fun getDrivers(season: String, round: String) {
        ErgastApi.retrofitService.getDrivers(season, round)
            .enqueue(object : Callback<ResponseDrivers> {
                override fun onResponse(
                    call: Call<ResponseDrivers>,
                    response: Response<ResponseDrivers>
                ) {
                    drivers =
                        response.body()!!.MRDataDrivers.DriverTable.getDriverIdNameCollection()
                    populateDrivers()
                }

                override fun onFailure(call: Call<ResponseDrivers>, t: Throwable) {
                    t.message?.let { Log.e("driversFAILURE", it) }
                }
            })
    }

    private fun getRaces(season: String) {
        ErgastApi.retrofitService.getRaces(season).enqueue(object : Callback<ResponseRaces> {
            override fun onResponse(call: Call<ResponseRaces>, response: Response<ResponseRaces>) {
                races = response.body()!!.MRData.RaceTable.getRaceIdNameCollection()
                populateRaces()
            }

            override fun onFailure(call: Call<ResponseRaces>, t: Throwable) {
                t.message?.let { Log.e("racesFAILURE", it) }
            }
        })
    }
}
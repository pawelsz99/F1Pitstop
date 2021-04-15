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
import androidx.navigation.fragment.findNavController
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


class HomeFragment : Fragment() {

    lateinit var races: IdNameCollection
    lateinit var drivers: IdNameCollection
    lateinit var driver1PitStops: DriverPitStops
    lateinit var driver2PitStops: DriverPitStops

    private lateinit var season: String
    private lateinit var round: String
    private lateinit var driverId1: String
    private lateinit var driverId2: String
    private val bundle = Bundle()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onResume() {
        super.onResume()
        enableAllSelectFields()
        activity?.title = resources.getString(R.string.app_name)
        populateSeasons()
    }

    private fun enableAllSelectFields() {
        if (binding.selectRace.editText?.text.toString() != "") {
            populateRaces()
            populateDrivers()
            populateDrivers2()
            binding.buttonCompare.isEnabled = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        populateSeasons()


        binding.buttonCompare.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_resultFragment, bundle)
        }

        return binding.root
    }

    private fun packDataUpInBundle() {
        binding.buttonCompare.isEnabled = true

        bundle.putString("season", season)
        bundle.putString("raceName", races.getNameById(round))
        bundle.putString("driver1Name", drivers.getNameById(driverId1))
        bundle.putString("driver2Name", drivers.getNameById(driverId2))
        bundle.putParcelable("driver1PitStop", driver1PitStops)
        bundle.putParcelable("driver2PitStop", driver2PitStops)

    }

    private fun populateDrivers2() {
        binding.selectDriver2.isEnabled = true


        // removing the first driver from list to be displayed
        val driver1Id = driver1PitStops.driverId.toString()
        val raceNames = ArrayList(drivers.nameList)
        raceNames.remove(drivers.getNameById(driver1Id))
        val adapter = ArrayAdapter(requireContext(), R.layout.home_list_item, raceNames)

        (binding.selectDriver2.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.selectDriver2List.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l -> onDriver2Selected() }
    }

    private fun populateDrivers() {
        binding.selectDriver1.isEnabled = true

        val raceNames = ArrayList(drivers.nameList)
        // if the 2. driver is already selected then remove him from the list
        if (this::driver2PitStops.isInitialized) {
            val driver2Id = driver2PitStops.driverId.toString()
            raceNames.remove(drivers.getNameById(driver2Id))
        }

        val adapter = ArrayAdapter(requireContext(), R.layout.home_list_item, raceNames)
        (binding.selectDriver1.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.selectDriver1List.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l -> onDriver1Selected() }

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
        binding.selectRace.isEnabled = true

        val raceNames = ArrayList(races.nameList)
        val adapter = ArrayAdapter(requireContext(), R.layout.home_list_item, raceNames)
        (binding.selectRace.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.selectRaceList.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l -> onRaceSelected() }
    }

    private fun populateSeasons() {
        val listSeasons = mutableListOf<String>()
        for (i in 2011..Calendar.getInstance().get(Calendar.YEAR)) {
            listSeasons.add("$i")
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.home_list_item, listSeasons)
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
                        // if the driver has no pit stops then create an empty DriverPitStop object
                        driver1PitStops =
                            if (response.body()!!.MRDataPitStops.RaceTable2.Races2.isEmpty()) {
                                DriverPitStops(
                                    "",
                                    ArrayList(),
                                    ArrayList(),
                                    ArrayList(),
                                    ArrayList()
                                )
                            } else {
                                response.body()!!.MRDataPitStops.RaceTable2.Races2[0].getDriverPitStops()
                            }

                        populateDrivers2()

                    } else {
                        // if the driver has no pit stops then create an empty DriverPitStop object
                        driver2PitStops =
                            if (response.body()!!.MRDataPitStops.RaceTable2.Races2.isEmpty()) {
                                DriverPitStops(
                                    "",
                                    ArrayList(),
                                    ArrayList(),
                                    ArrayList(),
                                    ArrayList()
                                )
                            } else {
                                response.body()!!.MRDataPitStops.RaceTable2.Races2[0].getDriverPitStops()
                            }

                        packDataUpInBundle()
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
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
    lateinit var racesNameList: ArrayList<SelectListData>
    lateinit var driversNameList: ArrayList<SelectListData>
    private var pitStopDurationList1 = mutableListOf<String>()
    private var pitStopDurationList2 = mutableListOf<String>()

    private lateinit var season: String
    private lateinit var round: String
    private lateinit var driverId1: String
    private lateinit var driverId2: String
    val bundleRaces = Bundle()
    val bundleDrivers = Bundle()

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        // Select Season
        populateSeasons(binding)




        binding.buttonCompare.setOnClickListener {
            Log.e(
                "All data",
                "season $season, round $round, driver1 $driverId1, driver2 $driverId2"
            )
        }


        // hard coded values
        round = "1"
        driverId1 = "sainz"
        driverId2 = "vettel"
//        getRaces(season)
//        getDrivers(season, round)
//        pitStopDurationList1 = getPitStops(season, round, driverId1, pitStopDurationList1)
//        pitStopDurationList2 = getPitStops(season, round, driverId2, pitStopDurationList2)
        return binding.root
    }

    private fun populateDrivers(binding: FragmentHomeBinding) {
        Log.e("populate drivers", drivers.nameList.toString())
        val raceNames = ArrayList(drivers.nameList)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, raceNames)
        (binding.selectDriver1.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.selectDriver1List.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l -> onDriver1Selected(binding) }

        (binding.selectDriver2.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.selectDriver2List.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l -> onDriver2Selected(binding) }
    }

    private fun onDriver1Selected(binding: FragmentHomeBinding) {
        driverId1 = drivers.getIdByName(binding.selectDriver1.editText?.text.toString())
        Log.e("driver 1 ", driverId1)

    }

    private fun onDriver2Selected(binding: FragmentHomeBinding) {
        driverId2 = drivers.getIdByName(binding.selectDriver2.editText?.text.toString())
        Log.e("driver 1 ", driverId2)
    }

    private fun populateRaces(binding: FragmentHomeBinding) {
        Log.e("populate races", races.nameList.toString())
        val raceNames = ArrayList(races.nameList)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, raceNames)
        (binding.selectRace.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.selectRaceList.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l -> onRaceSelected(binding) }
    }

    private fun populateSeasons(binding: FragmentHomeBinding) {
        val listSeasons = mutableListOf<String>()
        for (i in 2011..Calendar.getInstance().get(Calendar.YEAR)) {
            listSeasons.add("$i")
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, listSeasons)
        (binding.selectSeason.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.selectSeasonList.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                onSeasonSelected(binding)
            }
    }

    private fun onRaceSelected(binding: FragmentHomeBinding) {

//        round = binding.selectRace.editText?.text.toString()
        round = races.getIdByName(binding.selectRace.editText?.text.toString())
        Log.e("round ", round)
        getDrivers(season, round)
    }

    private fun onSeasonSelected(binding: FragmentHomeBinding) {
        season = binding.selectSeason.editText?.text.toString()
        Log.e("season ", season)
        getRaces(season)
    }


    // TODO convert the races map, switch key - value

    private fun getPitStops(
        season: String,
        round: String,
        driverId: String,
        pitStopDurationList: MutableList<String>
    ): MutableList<String> {
        ErgastApi.retrofitService.getPitStops(season, round, driverId)
            .enqueue(object : Callback<ResponsePitStops> {
                override fun onResponse(
                    call: Call<ResponsePitStops>,
                    response: Response<ResponsePitStops>
                ) {
//                    Log.e(
//                        "pitStopRESPONSE $driverId",
//                        response.body()!!.MRDataPitStops.RaceTable2.Races2[0].getPitStopDurationList()
//                            .toString()
//                    )
                    pitStopDurationList.addAll(response.body()!!.MRDataPitStops.RaceTable2.Races2[0].getPitStopDurationList())
                }

                override fun onFailure(call: Call<ResponsePitStops>, t: Throwable) {
                    t.message?.let { Log.e("pitStopFAILURE", it) }
                }

            })

        return pitStopDurationList
    }

    private fun getDrivers(season: String, round: String) {
        ErgastApi.retrofitService.getDrivers(season, round)
            .enqueue(object : Callback<ResponseDrivers> {
                override fun onResponse(
                    call: Call<ResponseDrivers>,
                    response: Response<ResponseDrivers>
                ) {
                    Log.e("url", "s = $season, r = $round")
                    Log.e(
                        "getdrivername",
                        response.message()
                    )
                    drivers =
                        response.body()!!.MRDataDrivers.DriverTable.getDriverNameList()
//                    bundleDrivers.putParcelableArrayList("list", driversNameList)
                    populateDrivers(binding)
                }

                override fun onFailure(call: Call<ResponseDrivers>, t: Throwable) {
                    t.message?.let { Log.e("driversFAILURE", it) }
                }

            })
    }

    private fun getRaces(season: String) {
        ErgastApi.retrofitService.getRaces(season).enqueue(object : Callback<ResponseRaces> {
            override fun onResponse(call: Call<ResponseRaces>, response: Response<ResponseRaces>) {
                Log.e("racesRESPONSE", response.body().toString())
                Log.e(
                    "racesName",
                    response.body()!!.MRData.RaceTable.getRaceIdNameCollection().toString()
                )

                races = response.body()!!.MRData.RaceTable.getRaceIdNameCollection()
                populateRaces(binding)
//                bundleRaces.putParcelableArrayList("list", racesNameList)
            }

            override fun onFailure(call: Call<ResponseRaces>, t: Throwable) {
                t.message?.let { Log.e("racesFAILURE", it) }
            }

        })
    }
}
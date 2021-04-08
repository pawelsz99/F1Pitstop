package com.pawelsznuradev.f1pitstop

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.pawelsznuradev.f1pitstop.databinding.FragmentHomeBinding
import com.pawelsznuradev.f1pitstop.network.ErgastApi
import com.pawelsznuradev.f1pitstop.network.ResponseDrivers
import com.pawelsznuradev.f1pitstop.network.ResponsePitStops
import com.pawelsznuradev.f1pitstop.network.ResponseRaces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        // Select Season
        val listSeasons = mutableListOf<String>()
        for (i in 2011..Calendar.getInstance().get(Calendar.YEAR)) {
            listSeasons.add("$i")
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, listSeasons)
        (binding.selectSeason.editText as? AutoCompleteTextView)?.setAdapter(adapter)


        test(binding)
        Log.e("season selected ", season)
        Log.e("season selected ", "test")


        binding.buttonCompare.setOnClickListener {
            test(binding)
        }


        // hard coded values
        //  season = "2020"
        round = "1"
        driverId1 = "sainz"
        driverId2 = "vettel"


//        getRaces(season)
//        getDrivers(season, round)
//        pitStopDurationList1 = getPitStops(season, round, driverId1, pitStopDurationList1)
//        pitStopDurationList2 = getPitStops(season, round, driverId2, pitStopDurationList2)


        return binding.root
    }

    private fun test(binding: FragmentHomeBinding) {
        season = binding.selectSeason.editText?.text.toString()
        Log.e("season selected ", season)
        Log.e("season selected ", "test")
    }


    private fun selectDriver(): View.OnClickListener? {
//        Log.e("SelectDriver", "Start")
        getDrivers(season, round)
        return Navigation.createNavigateOnClickListener(
            R.id.action_homeFragment_to_selectFragment,
            bundleDrivers
        )
    }

    private fun selectRace(): View.OnClickListener? {
//        Log.e("SelectRace", "Start")
        getRaces(season)
        return Navigation.createNavigateOnClickListener(
            R.id.action_homeFragment_to_selectFragment,
            bundleRaces
        )
    }

    // TODO convert the function to return an list of objects


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
//                    Log.e(
//                        "driversResponse",
//                        response.body()!!.MRDataDrivers.DriverTable.getDriverNameList().toString()
//                    )

                    driversNameList =
                        response.body()!!.MRDataDrivers.DriverTable.getDriverNameList() as ArrayList<SelectListData>
                    bundleDrivers.putParcelableArrayList("list", driversNameList)
                }

                override fun onFailure(call: Call<ResponseDrivers>, t: Throwable) {
                    t.message?.let { Log.e("driversFAILURE", it) }
                }

            })
    }

    private fun getRaces(season: String) {
        ErgastApi.retrofitService.getRaces(season).enqueue(object : Callback<ResponseRaces> {
            override fun onResponse(call: Call<ResponseRaces>, response: Response<ResponseRaces>) {
//                Log.e("racesRESPONSE", response.body().toString())
//                Log.e("racesName", response.body()!!.MRData.RaceTable.getRaceNameList().toString())

                racesNameList =
                    response.body()!!.MRData.RaceTable.getRaceNameList() as ArrayList<SelectListData>
                bundleRaces.putParcelableArrayList("list", racesNameList)

            }

            override fun onFailure(call: Call<ResponseRaces>, t: Throwable) {
                t.message?.let { Log.e("racesFAILURE", it) }
            }

        })
    }

}
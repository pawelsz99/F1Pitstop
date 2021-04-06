package com.pawelsznuradev.f1pitstop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.pawelsznuradev.f1pitstop.network.ErgastApi
import com.pawelsznuradev.f1pitstop.network.ResponseDrivers
import com.pawelsznuradev.f1pitstop.network.ResponsePitStops
import com.pawelsznuradev.f1pitstop.network.ResponseRaces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var racesNameList: List<String>
    lateinit var driversNameList: List<String>
    private var pitStopDurationList1 = mutableListOf<String>()
    private var pitStopDurationList2 = mutableListOf<String>()


    lateinit var roundSelected: String
    lateinit var driverId1: String
    lateinit var driverId2: String

    var season = "2020" // change this when adding season selection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // hard coded values
        roundSelected = "1"
        driverId1 = "sainz"
        driverId2 = "vettel"


        getRaces()
        getDrivers()
        pitStopDurationList1 = getPitStops(driverId1, pitStopDurationList1)
        pitStopDurationList2 = getPitStops(driverId2, pitStopDurationList2)


    }

    private fun getPitStops(
        driverId: String,
        pitStopDurationList: MutableList<String>
    ): MutableList<String> {
        ErgastApi.retrofitService.getPitStops(season, roundSelected, driverId)
            .enqueue(object : Callback<ResponsePitStops> {
                override fun onResponse(
                    call: Call<ResponsePitStops>,
                    response: Response<ResponsePitStops>
                ) {

                    Log.e(
                        "pitStopRESPONSE $driverId",
                        response.body()!!.MRDataPitStops.RaceTable2.Races2[0].getPitStopDurationList()
                            .toString()
                    )



                    pitStopDurationList.addAll(response.body()!!.MRDataPitStops.RaceTable2.Races2[0].getPitStopDurationList())

                }

                override fun onFailure(call: Call<ResponsePitStops>, t: Throwable) {
                    t.message?.let { Log.e("pitStopFAILURE", it) }
                }

            })

        return pitStopDurationList
    }

    private fun getDrivers() {
        ErgastApi.retrofitService.getDrivers(season, roundSelected)
            .enqueue(object : Callback<ResponseDrivers> {
                override fun onResponse(
                    call: Call<ResponseDrivers>,
                    response: Response<ResponseDrivers>
                ) {
                    Log.e(
                        "driversResponse",
                        response.body()!!.MRDataDrivers.DriverTable.getDriverNameList().toString()
                    )

                    driversNameList =
                        response.body()!!.MRDataDrivers.DriverTable.getDriverNameList()

                }

                override fun onFailure(call: Call<ResponseDrivers>, t: Throwable) {
                    t.message?.let { Log.e("driversFAILURE", it) }
                }

            })
    }

    private fun getRaces() {
        ErgastApi.retrofitService.getRaces("2020").enqueue(object : Callback<ResponseRaces> {
            override fun onResponse(call: Call<ResponseRaces>, response: Response<ResponseRaces>) {
                Log.e("racesRESPONSE", response.body().toString())
                Log.e("racesName", response.body()!!.MRData.RaceTable.getRaceNameList().toString())

                racesNameList = response.body()!!.MRData.RaceTable.getRaceNameList()

            }

            override fun onFailure(call: Call<ResponseRaces>, t: Throwable) {
                t.message?.let { Log.e("racesFAILURE", it) }
            }

        })
    }
}
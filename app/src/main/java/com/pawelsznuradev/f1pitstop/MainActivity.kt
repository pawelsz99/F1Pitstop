package com.pawelsznuradev.f1pitstop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pawelsznuradev.f1pitstop.network.ErgastApi
import com.pawelsznuradev.f1pitstop.network.ResponseRaces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var racesNameList : List<String>
    lateinit var raceSelected : String

    var season = "2020" // change this when adding season selection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRaces()
        getDrivers()

    }

    private fun getDrivers() {
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
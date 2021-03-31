package com.pawelsznuradev.f1pitstop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pawelsznuradev.f1pitstop.network.ErgastApi
import com.pawelsznuradev.f1pitstop.network.MRData
import com.pawelsznuradev.f1pitstop.network.MRDataRaces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRaces()



    }

    private fun getRaces(){
        ErgastApi.retrofitService.getRaces("2020").enqueue(object : Callback<MRData> {
            override fun onResponse(call: Call<MRData>, response: Response<MRData>) {
                Log.e("racesRESPONSE", response.body()!!.MRData.RaceTable.Races.size.toString())
                Log.e("racesRESPONSE", response.raw().toString())



            }

            override fun onFailure(call: Call<MRData>, t: Throwable) {
                t.message?.let { Log.e("racesFAILURE", it) }
            }

        })
    }
}
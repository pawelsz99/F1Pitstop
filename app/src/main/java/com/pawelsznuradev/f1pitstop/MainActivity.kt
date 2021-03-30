package com.pawelsznuradev.f1pitstop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pawelsznuradev.f1pitstop.network.ErgastApi
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
        ErgastApi.retrofitService.getRaces("2020").enqueue(object : Callback<MRDataRaces> {
            override fun onResponse(call: Call<MRDataRaces>, response: Response<MRDataRaces>) {
                Log.e("racesRESPONSE", "1")
                Log.e("racesRESPONSE", response.body().toString())
                Log.e("racesRESPONSE", response.raw().toString())


            }

            override fun onFailure(call: Call<MRDataRaces>, t: Throwable) {
                Log.e("racesFAILURE", t.message)
            }

        })
    }
}
package com.pawelsznuradev.f1pitstop.network


import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://ergast.com/api/f1/"


private val gson = GsonBuilder()
    .setLenient()
    .create()


private
val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()


object ErgastApi {
    val retrofitService: ErgastApiService by lazy { retrofit.create(ErgastApiService::class.java) }
}

interface ErgastApiService {
    @GET("{season}/races.json")
    fun getRaces(@Path("season") season: String): Call<ResponseRaces>

    @GET("{season}/{round}/drivers.json")
    fun getDrivers(
        @Path("season") season: String,
        @Path("round") round: String
    ): Call<ResponseDrivers>
}

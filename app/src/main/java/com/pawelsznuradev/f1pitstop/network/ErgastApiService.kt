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


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()


object ErgastApi {
    val retrofitService: ErgastApiService by lazy { retrofit.create(ErgastApiService::class.java) }
}

/**
 * Ergast api service
 *
 * @constructor Create empty Ergast api service
 */
interface ErgastApiService {
    /**
     * Get races
     *
     * @param season
     * @return [ResponseRaces]
     */
    @GET("{season}/races.json")
    fun getRaces(@Path("season") season: String): Call<ResponseRaces>

    /**
     * Get drivers
     *
     * @param season
     * @param round
     * @return [ResponseDrivers]
     */
    @GET("{season}/{round}/drivers.json")
    fun getDrivers(
        @Path("season") season: String,
        @Path("round") round: String
    ): Call<ResponseDrivers>

    /**
     * Get pit stops
     *
     * @param season
     * @param round
     * @param driverId
     * @return [ResponsePitStops]
     */
    @GET("{season}/{round}/drivers/{driverId}/pitstops.json")
    fun getPitStops(
        @Path("season") season: String,
        @Path("round") round: String,
        @Path("driverId") driverId: String
    ): Call<ResponsePitStops>
}

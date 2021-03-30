package com.pawelsznuradev.f1pitstop.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://ergast.com/api/f1/"


/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val gson = GsonBuilder()
    .setLenient()
    .create()


    /**
     * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
     * object.
     */
    private
val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()


interface ErgastApiService {
    @GET("{season}/races.json")
    fun getRaces(@Path("season") season: String): Call<MRDataRaces>
}

object ErgastApi {
    val retrofitService: ErgastApiService by lazy { retrofit.create(ErgastApiService::class.java) }
}

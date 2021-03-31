package com.pawelsznuradev.f1pitstop.network

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class MRData(
    val MRData: MRDataRaces
)

data class MRDataRaces(
    val xmlns: String,
    val series: String,
    val limit: String,
    val offset: String,
    val total: String,
    val RaceTable: RaceTable
) {
    init {
        Log.e("MRDataRaces init", xmlns)
    }

//    override fun toString(): String {
//        return "$xmlns / $series / $limit / $offset / $total / $RaceTable / "
//    }
}


data class RaceTable(
    val season: String,
    val Races: List<Races>
)

data class Races(
    val season: String,
    val round: String,
    val url: String?,
    val raceName: String,
    val Circuit: Circuit,
    val date: String,
    val time: String
)

data class Circuit(
    val circuitId: String,
    val url: String?,
    val name: String,
    val Location: Location

)

data class Location(
    val lat: Float?,
    val lon: Float?,
    val locality: String?,
    val country: String?
)

data class MRDataDrivers(
    val series: String,
    val limit: String,
    val offset: String,
    val total: String,
    val DriverTable: DriverTable
)

data class DriverTable(
    val season: String,
    val round: String,
    val Drivers: List<Drivers>
)

data class Drivers(
    val driverId: String,
    val permanentNumber: String,
    val code: String,
    val url: String?,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String
)

data class MRDataPitStops(
    val series: String,
    val limit: String,
    val offset: String,
    val total: String,
    @SerializedName("RaceTable")
    val RaceTable2: RaceTable2
)

data class RaceTable2(
    val season: String,
    @SerializedName("Races")
    val Races2: List<Races2>
)

data class Races2(
    val season: String,
    val round: String,
    val url: String?,
    val raceName: String,
    val circuit: Circuit,
    val date: String,
    val time: String,
    val PitStops: List<PitStops>
)

data class PitStops(
    val driverId: String,
    val lap: String,
    val stop: String,
    val time: String,
    val duration: Float
)
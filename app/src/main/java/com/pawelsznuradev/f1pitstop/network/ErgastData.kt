package com.pawelsznuradev.f1pitstop.network

import com.google.gson.annotations.SerializedName
import com.pawelsznuradev.f1pitstop.IdNameCollection

data class ResponseRaces(
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
    override fun toString(): String {
        return "$xmlns / $series / $limit / $offset / $total / $RaceTable / "
    }
}


data class RaceTable(
    val season: String,
    val Races: List<Races>
) {
    fun getRaceIdNameCollection(): IdNameCollection {
        val racesNameList = mutableListOf<String>()
        val racesIdList = mutableListOf<String>()
        Races.forEach {
            racesNameList.add(it.raceName)
            racesIdList.add(it.round)
        }
        return IdNameCollection(racesIdList, racesNameList)
    }
}

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

data class ResponseDrivers(
    @SerializedName("MRData")
    val MRDataDrivers: MRDataDrivers
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
) {
    fun getDriverNameList(): IdNameCollection {
        val driversNameList = mutableListOf<String>()
        val driversIdList = mutableListOf<String>()
        Drivers.forEach {
            driversNameList.add("${it.givenName} ${it.familyName}")
            driversIdList.add(it.driverId)
        }
        return IdNameCollection(driversIdList, driversNameList)
    }

}

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

data class ResponsePitStops(
    @SerializedName("MRData")
    val MRDataPitStops: MRDataPitStops
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
) {
    fun getPitStopDurationList(): MutableList<String> {
        val pitStopDurationList: MutableList<String> = mutableListOf()
        PitStops.forEach {
            pitStopDurationList.add("${it.duration} ")
        }
        return pitStopDurationList
    }
}

data class PitStops(
    val driverId: String,
    val lap: String,
    val stop: String,
    val time: String,
    val duration: Float
)
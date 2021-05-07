package com.pawelsznuradev.f1pitstop.network

import com.google.gson.annotations.SerializedName
import com.pawelsznuradev.f1pitstop.DriverPitStops
import com.pawelsznuradev.f1pitstop.IdNameCollection
import java.util.ArrayList

/**
 * Created by Pawel Sznura on 30/03/2021.
 */

/**
 * Response races
 *
 * @property MRData
 * @constructor Create empty Response races
 */
data class ResponseRaces(
    val MRData: MRDataRaces
)

/**
 * M r data races
 *
 * @property xmlns
 * @property series
 * @property limit
 * @property offset
 * @property total
 * @property RaceTable
 * @constructor Create empty M r data races
 */
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


/**
 * Race table
 *
 * @property season
 * @property Races
 * @constructor Create empty Race table
 */
data class RaceTable(
    val season: String,
    val Races: List<Races>
) {
    /**
     * Get race id name collection
     *
     * @return all races in a form of IdNameCollection
     */
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

/**
 * Races
 *
 * @property season
 * @property round
 * @property url
 * @property raceName
 * @property Circuit
 * @property date
 * @property time
 * @constructor Create empty Races
 */
data class Races(
    val season: String,
    val round: String,
    val url: String?,
    val raceName: String,
    val Circuit: Circuit,
    val date: String,
    val time: String
)

/**
 * Circuit
 *
 * @property circuitId
 * @property url
 * @property name
 * @property Location
 * @constructor Create empty Circuit
 */
data class Circuit(
    val circuitId: String,
    val url: String?,
    val name: String,
    val Location: Location

)

/**
 * Location
 *
 * @property lat
 * @property lon
 * @property locality
 * @property country
 * @constructor Create empty Location
 */
data class Location(
    val lat: Float?,
    val lon: Float?,
    val locality: String?,
    val country: String?
)

/**
 * Response drivers
 *
 * @property MRDataDrivers
 * @constructor Create empty Response drivers
 */
data class ResponseDrivers(
    @SerializedName("MRData")
    val MRDataDrivers: MRDataDrivers
)

/**
 * M r data drivers
 *
 * @property series
 * @property limit
 * @property offset
 * @property total
 * @property DriverTable
 * @constructor Create empty M r data drivers
 */
data class MRDataDrivers(
    val series: String,
    val limit: String,
    val offset: String,
    val total: String,
    val DriverTable: DriverTable
)

/**
 * Driver table
 *
 * @property season
 * @property round
 * @property Drivers
 * @constructor Create empty Driver table
 */
data class DriverTable(
    val season: String,
    val round: String,
    val Drivers: List<Drivers>
) {
    /**
     * Get driver id name collection
     *
     * @return all drivers in a form of IdNameCollection
     */
    fun getDriverIdNameCollection(): IdNameCollection {
        val driversNameList = mutableListOf<String>()
        val driversIdList = mutableListOf<String>()
        Drivers.forEach {
            driversNameList.add("${it.givenName} ${it.familyName}")
            driversIdList.add(it.driverId)
        }
        return IdNameCollection(driversIdList, driversNameList)
    }
}

/**
 * Drivers
 *
 * @property driverId
 * @property permanentNumber
 * @property code
 * @property url
 * @property givenName
 * @property familyName
 * @property dateOfBirth
 * @property nationality
 * @constructor Create empty Drivers
 */
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

/**
 * Response pit stops
 *
 * @property MRDataPitStops
 * @constructor Create empty Response pit stops
 */
data class ResponsePitStops(
    @SerializedName("MRData")
    val MRDataPitStops: MRDataPitStops
)

/**
 * M r data pit stops
 *
 * @property series
 * @property limit
 * @property offset
 * @property total
 * @property RaceTable2
 * @constructor Create empty M r data pit stops
 */
data class MRDataPitStops(
    val series: String,
    val limit: String,
    val offset: String,
    val total: String,
    @SerializedName("RaceTable")
    val RaceTable2: RaceTable2
)

/**
 * Race table2
 *
 * @property season
 * @property Races2
 * @constructor Create empty Race table2
 */
data class RaceTable2(
    val season: String,
    @SerializedName("Races")
    val Races2: List<Races2>

)


/**
 * Races2
 *
 * @property season
 * @property round
 * @property url
 * @property raceName
 * @property circuit
 * @property date
 * @property time
 * @property PitStops
 * @constructor Create empty Races2
 */
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
    /**
     * Get driver pit stops
     *
     * @return all pit stops for the driver
     */
    fun getDriverPitStops(): DriverPitStops {
        val driverId = PitStops[0].driverId
        val stop = mutableListOf<String>()
        val lap = mutableListOf<String>()
        val time = mutableListOf<String>()
        val duration = mutableListOf<String>()
        PitStops.forEach {
            stop.add(it.stop)
            lap.add(it.lap)
            time.add(it.time)
            duration.add(it.duration)
        }
        return DriverPitStops(
            driverId,
            stop as ArrayList<String>,
            lap as ArrayList<String>,
            time as ArrayList<String>,
            duration as ArrayList<String>
        )
    }
}

/**
 * Pit stops
 *
 * @property driverId
 * @property lap
 * @property stop
 * @property time
 * @property duration
 * @constructor Create empty Pit stops
 */
data class PitStops(
    val driverId: String,
    val lap: String,
    val stop: String,
    val time: String,
    val duration: String
)

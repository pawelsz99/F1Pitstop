package com.pawelsznuradev.f1pitstop

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

/**
 * Created by Pawel Sznura on 10/04/2021.
 */

/**
 * Id name collection
 * allows to get the name by id and the other way around
 * basically a dictionary where you can get a key by value
 *
 * (in data classes getters and setters are automatically created)
 *
 * @property idList
 * @property nameList
 * @constructor Create empty Id name collection
 */
data class IdNameCollection(val idList: List<String>, val nameList: List<String>) {

    /**
     * Get id by name
     *
     * @param name
     * @return id or ""
     */
    fun getIdByName(name: String): String {
        return if (name in nameList) {
            val position = nameList.indexOf(name)
            idList[position]
        } else ""
    }

    /**
     * Get name by id
     *
     * @param id
     * @return name or ""
     */
    fun getNameById(id: String): String {
        return if (id in idList) {
            val position = idList.indexOf(id)
            nameList[position]
        } else ""
    }
}

/**
 * Driver pit stops
 * a parcelable data class to put in into a bundle
 *
 * @property driverId
 * @property stop
 * @property lap
 * @property time
 * @property duration
 * @constructor Create empty Driver pit stops
 */
data class DriverPitStops(
    val driverId: String?,
    val stop: ArrayList<String>?,
    val lap: ArrayList<String>?,
    val time: ArrayList<String>?,
    val duration: ArrayList<String>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    )


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(driverId)
        parcel.writeStringList(stop)
        parcel.writeStringList(lap)
        parcel.writeStringList(time)
        parcel.writeStringList(duration)
    }

    companion object CREATOR : Parcelable.Creator<DriverPitStops> {

        override fun createFromParcel(parcel: Parcel): DriverPitStops {
            return DriverPitStops(parcel)
        }

        override fun newArray(size: Int): Array<DriverPitStops?> {
            return arrayOfNulls(size)
        }
    }

}

/**
 * Result data
 * used in the [ResultFragment] for passing data to the RecyclerView
 *
 * @property pos1
 * @property pos2
 * @property pos3
 * @constructor Create empty Result data
 */
data class ResultData(val pos1: String, val pos2: String, val pos3: String) {
    constructor(pos1: String) : this(pos1, "", "")
    constructor(pos1: String, pos2: String) : this(pos1, pos2, "")

}

/**
 * Pit stop time (minutes:seconds.milliseconds)
 * allows to save pit stops longer than 60 seconds
 *
 * @property timeString
 * @constructor Create empty Pit stop time
 */
data class PitStopTime(val timeString: String) {
    private var minutes: Int = timeString.substringBefore(":", "0").toInt()
    private var seconds: Float = try {
        timeString.substringAfter(":", timeString).toFloat()
    } catch (e: NumberFormatException) { // when the input cannot be converted to a float number
        0.0F
    }

    /**
     * Get total time in seconds
     *
     * @return total time in seconds
     */
    fun getTotalTime(): Float {
        var total = 0.0
        total += minutes * 60
        total += seconds
        return total.toFloat()
    }
}
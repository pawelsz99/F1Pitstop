package com.pawelsznuradev.f1pitstop

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

/**
 * Created by Pawel Sznura on 10/04/2021.
 */
data class IdNameCollection(val idList: List<String>, val nameList: List<String>) {

    fun getIdByName(name: String): String {
        if (name in nameList) {
            val position = nameList.indexOf(name)
            return idList[position]
        } else return ""
    }

    fun getNameById(id: String): String {
        if (id in idList) {
            val position = idList.indexOf(id)
            return nameList[position]
        } else return ""
    }
}

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

data class ResultData(val pos1: String, val pos2: String, val pos3: String) {
    constructor(pos1: String) : this(pos1, "", "")
    constructor(pos1: String, pos2: String) : this(pos1, pos2, "")

}
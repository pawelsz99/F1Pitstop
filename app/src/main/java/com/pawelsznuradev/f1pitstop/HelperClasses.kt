package com.pawelsznuradev.f1pitstop

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

/**
 * Created by Pawel Sznura on 10/04/2021.
 */
data class IdNameCollection(val idList: List<String>, val nameList: List<String>) {

    fun getIdByName(name: String): String {
        val position = nameList.indexOf(name)
        return idList[position]
    }

    fun getNameById(id: String): String {
        val position = idList.indexOf(id)
        return nameList[position]
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
package com.pawelsznuradev.f1pitstop

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Pawel Sznura on 07/04/2021.
 */
data class SelectListData(val id: String?, val content: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SelectListData> {
        override fun createFromParcel(parcel: Parcel): SelectListData {
            return SelectListData(parcel)
        }

        override fun newArray(size: Int): Array<SelectListData?> {
            return arrayOfNulls(size)
        }
    }
}

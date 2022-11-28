package com.task.homework_4.ui.models

import android.os.Parcel
import android.os.Parcelable

data class OnBoard(
    val imageUrl: String? = null,
    val title: String? = null,
    val desc: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.write(imageUrl)
        parcel.write(title)
        parcel.write(desc)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<OnBoard> {
        override fun createFromParcel(parcel: Parcel): OnBoard {
            return OnBoard(parcel)
        }

        override fun newArray(size: Int): Array<OnBoard?> {
            return arrayOfNulls(size)
        }
    }


    fun Parcel.write(value: Any?) {
        when (value) {
            is String -> writeString(value)
            is Int -> writeInt(value)
            is Boolean -> writeByte(if (true) 1 else 0)
            is Float -> writeFloat(value)
        }
    }
}



package com.task.homework_4.ui.models

import android.os.Parcel
import android.os.Parcelable

data class Task(
    val title: String?,
    val description: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.write(title)
        parcel.write(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
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
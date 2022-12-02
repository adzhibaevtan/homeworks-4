package com.task.homework_4.ui.models

import android.os.Parcel
import android.os.Parcelable
;
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    val title: String? ,
    val description: String?,
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.write(title)
        parcel.write(description)
    }

    override fun describeContents() = 0

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
package org.abubaker.projemanag.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Data model class for Task.
 */
data class Task(

    // Title
    var title: String = "",

    // Created By
    val createdBy: String = ""

) : Parcelable {

    constructor(source: Parcel) : this(

        // Title
        source.readString()!!,

        // Created By
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {

        // Title
        writeString(title)

        // Created By
        writeString(createdBy)
    }


    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Task> = object : Parcelable.Creator<Task> {
            override fun createFromParcel(source: Parcel): Task = Task(source)
            override fun newArray(size: Int): Array<Task?> = arrayOfNulls(size)
        }

    }

}
package org.abubaker.projemanag.models

import android.os.Parcel
import android.os.Parcelable

data class Board(
    val name: String = "",
    val image: String = "",
    val createdBy: String = "",
    val assignedTo: ArrayList<String> = ArrayList()
) : Parcelable {
    constructor(source: Parcel) : this(

        // Name
        source.readString()!!,

        // Image
        source.readString()!!,

        // Created By
        source.readString()!!,

        // Assigned To
        source.createStringArrayList()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {

        // Name
        writeString(name)

        // Image
        writeString(image)

        // Created By
        writeString(createdBy)

        // Assigned To
        writeStringList(assignedTo)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Board> = object : Parcelable.Creator<Board> {
            override fun createFromParcel(source: Parcel): Board = Board(source)
            override fun newArray(size: Int): Array<Board?> = arrayOfNulls(size)
        }
    }
}
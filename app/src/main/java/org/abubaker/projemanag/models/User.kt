package org.abubaker.projemanag.models

import android.os.Parcel
import android.os.Parcelable

data class User(

    // ID of the user
    val id: String = "",

    // Username
    val name: String = "",

    // Email
    val email: String = "",

    // Profile Picture (path)
    val image: String = "",

    // Mobile Number
    val mobile: Long = 0,

    // Token
    val fcmToken: String = ""

) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readLong(),
        source.readString()!!
    )

    override fun describeContents() = 0

    // Write values using variables defined in the "data class User" above
    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(email)
        writeString(image)
        writeLong(mobile)
        writeString(fcmToken)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}
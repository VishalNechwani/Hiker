package com.example.hiker.utils

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Component(
    @SerializedName("namer")
    val namer:String,
    @SerializedName("valuer")
    val valuer:String,
    @SerializedName("toadder")
    val toadder:Boolean
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Component> {
        override fun createFromParcel(parcel: Parcel): Component {
            return Component(parcel)
        }

        override fun newArray(size: Int): Array<Component?> {
            return arrayOfNulls(size)
        }
    }
}
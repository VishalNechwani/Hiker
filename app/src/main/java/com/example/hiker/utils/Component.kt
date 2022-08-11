package com.example.hiker.utils

import com.google.gson.annotations.SerializedName

data class Component(
    @SerializedName("namer")
    val namer:String,
    @SerializedName("valuer")
    val valuer:String,
    @SerializedName("toadder")
    val toadder:Boolean
    ) {
}
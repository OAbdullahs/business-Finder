package com.abdullahalomair.businessfinder.model.wathermodel.forecats

import com.google.gson.annotations.SerializedName


data class ForeCastDay (
     val date : String = "",
    @SerializedName("date_epoch") val date_epoch : Int = 0,
    @SerializedName("day") val day : Day = Day(),
     val hour : List<Hour> = emptyList()
)
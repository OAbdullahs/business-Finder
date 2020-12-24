package com.abdullahalomair.businessfinder.model.wathermodel.forecats

import android.os.Parcelable
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ForeCastDay (
    val date : String = "",
    @SerializedName("date_epoch") val date_epoch : Int = 0,
    @SerializedName("day") val day : Day = Day(),
    val hour : List<Hour> = emptyList()
): Parcelable
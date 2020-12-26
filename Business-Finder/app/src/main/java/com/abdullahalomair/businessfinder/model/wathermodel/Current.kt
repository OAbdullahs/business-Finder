package com.abdullahalomair.businessfinder.model.wathermodel

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Current (
    @SerializedName("last_updated_epoch") val lastUpdatedEpoch : Int = 0,
    @SerializedName("last_updated") val lastUpdated : String = "",
    @SerializedName("temp_c") val tempC : Double = -20.0,
    @SerializedName("temp_f") val tempF : Double = 0.0,
    @SerializedName("is_day") val isDay : Int = 0,
    @Embedded val condition : Condition = Condition()
):Parcelable
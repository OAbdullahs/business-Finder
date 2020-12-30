package com.abdullahalomair.businessfinder.model.wathermodel

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Current (
    @SerializedName("last_updated_epoch") var lastUpdatedEpoch : Int = 0,
    @SerializedName("last_updated") var lastUpdated : String = "",
    @SerializedName("temp_c") var tempC : Double = -20.0,
    @SerializedName("temp_f") var tempF : Double = 0.0,
    @SerializedName("is_day") var isDay : Int = 0,
    @Embedded var condition : Condition = Condition()
):Parcelable
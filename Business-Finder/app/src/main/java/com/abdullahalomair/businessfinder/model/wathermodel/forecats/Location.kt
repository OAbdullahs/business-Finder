package com.abdullahalomair.businessfinder.model.wathermodel.forecats

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location (
    var name : String = "",
    var region : String = "",
    var country : String ="",
    var lat : Double = 0.0,
    var lon : Double = 0.0,
    @SerializedName("tz_id") var tzId : String = "",
    @SerializedName("localtime_epoch") var localtimeEpoch : Int =0,
    var localtime : String = ""
):Parcelable
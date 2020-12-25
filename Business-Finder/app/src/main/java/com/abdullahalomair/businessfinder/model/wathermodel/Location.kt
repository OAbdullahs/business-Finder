package com.abdullahalomair.businessfinder.model.wathermodel

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location (
	 val name : String = "",
	 val region : String = "",
	 val country : String = "",
	 val lat : Double = 0.0,
	 val lon : Double =0.0,
	 val tz_id : String = "",
	@SerializedName("localtime_epoch") val localtimeEpoch : Int = 0,
	 val localtime : String = ""
):Parcelable
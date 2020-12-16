package com.abdullahalomair.businessfinder.model.wathermodel

import com.google.gson.annotations.SerializedName

data class Location (
	 val name : String,
	 val region : String,
	 val country : String,
	 val lat : Double,
	 val lon : Double,
	 val tz_id : String,
	@SerializedName("localtime_epoch") val localtimeEpoch : Int,
	 val localtime : String
)
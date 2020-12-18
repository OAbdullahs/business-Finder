package com.abdullahalomair.businessfinder.model.yelpmodel

import com.google.gson.annotations.SerializedName




data class Hours (

	val open : List<Open>,
	@SerializedName("hours_type") val hoursType : String,
	@SerializedName("is_open_now") val isOpenNow : Boolean
)
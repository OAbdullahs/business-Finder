package com.abdullahalomair.businessfinder.model.yelpmodel

import com.google.gson.annotations.SerializedName




data class Open (
	@SerializedName("is_overnight") val isOvernight : Boolean,
	val start : Int,
 	val end : Int,
 	val day : Int
)
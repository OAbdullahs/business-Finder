package com.abdullahalomair.businessfinder.model.yelpmodel

import com.google.gson.annotations.SerializedName





data class LocationData (
	val address1 : String = "",
	val address2 : String = "",
	val address3 : String = "",
	val city : String = "",
	@SerializedName("zip_code") val zipCode : Int = 0,
	 val country : String = "",
	 val state : String = "",
	@SerializedName("display_address") val displayAddress : List<String> = emptyList()
)
package com.abdullahalomair.businessfinder.model.yelpmodel




import com.google.gson.annotations.SerializedName


data class Businesses (
	val id : String = "",
	val alias : String = "",
	val name : String = "",
	@SerializedName("image_url") val imageUrl : String = "",
	@SerializedName("is_closed") val isClosed : Boolean = false,
	val url : String = "",
	@SerializedName("review_count") val reviewCount : Int = 0,
	val categories : List<Categories> = emptyList(),
	val rating : Double = 0.0,
	val coordinates : Coordinates = Coordinates(0.0,0.0),
	val transactions : List<String> = emptyList(),
	val price : String = "",
	@SerializedName("location") val locationData : LocationData = LocationData(""),
	val phone : String = "",
	@SerializedName("display_phone") val displayPhone : String = "",
	val distance : Double = 0.0
)
package com.abdullahalomair.businessfinder.model.yelpmodel


data class BusinessesList (
	val businesses : List<Businesses> = emptyList(),
	val total : Int = 0,
	val region : Region = Region()
)
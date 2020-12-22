package com.abdullahalomair.businessfinder.model.wathermodel.forecats

import com.google.gson.annotations.SerializedName
data class Forecast (
	@SerializedName("forecastday") val foreCastDay : List<ForeCastDay>
)
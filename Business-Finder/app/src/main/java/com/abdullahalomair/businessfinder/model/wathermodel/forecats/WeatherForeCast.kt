package com.abdullahalomair.businessfinder.model.wathermodel.forecats


import com.abdullahalomair.businessfinder.model.wathermodel.Current
import com.google.gson.annotations.SerializedName



data class WeatherForeCast (
    @SerializedName("location") val location : Location,
    @SerializedName("current") val current : Current,
    @SerializedName("forecast") val forecast : Forecast,
)
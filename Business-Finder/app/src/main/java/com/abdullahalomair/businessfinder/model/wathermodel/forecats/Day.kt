package com.abdullahalomair.businessfinder.model.wathermodel.forecats

import com.abdullahalomair.businessfinder.model.wathermodel.Condition
import com.google.gson.annotations.SerializedName



data class Day (

    @SerializedName("maxtemp_c") val maxTemp_c : Double = 0.0,
    @SerializedName("maxtemp_f") val maxTemp_f : Double= 0.0,
    @SerializedName("mintemp_c") val minTemp_c : Double= 0.0,
    @SerializedName("mintemp_f") val minTemp_f : Double = 0.0,
    @SerializedName("avgtemp_c") val avgTemp_c : Double= 0.0,
    @SerializedName("avgtemp_f") val avgTemp_f : Double= 0.0,
    @SerializedName("maxwind_mph") val maxWind_mph : Double = 0.0,
    @SerializedName("maxwind_kph") val maxWind_kph : Double = 0.0,
    @SerializedName("totalprecip_mm") val totalPrecip_mm : Double= 0.0,
    @SerializedName("totalprecip_in") val totalPrecip_in : Double= 0.0,
    @SerializedName("daily_will_it_rain") val daily_will_it_rain : Int= 0,
    @SerializedName("daily_chance_of_rain") val daily_chance_of_rain : Int =0,
    @SerializedName("daily_will_it_snow") val daily_will_it_snow : Int = 0,
    @SerializedName("daily_chance_of_snow") val daily_chance_of_snow : Int = 0,
    @SerializedName("condition") val condition : Condition= Condition(),
    @SerializedName("uv") val uv : Int = 0
)
package com.abdullahalomair.businessfinder.model.wathermodel

import com.google.gson.annotations.SerializedName


data class Current (
    @SerializedName("last_updated_epoch") val lastUpdatedEpoch : Int,
    @SerializedName("last_updated") val lastUpdated : String,
    @SerializedName("temp_c") val tempC : Double,
    @SerializedName("temp_f") val tempF : Double,
    @SerializedName("is_day") val isDay : Int,
     val condition : Condition,
    @SerializedName("wind_mph") val windMph : Double,
    @SerializedName("wind_kph") val windKph : Double,
    @SerializedName("wind_degree") val windDegree : Int,
    @SerializedName("pressure_mb") val pressureMb : Int,
    @SerializedName("pressure_in") val pressureIn : Double,
    @SerializedName("humidity") val humidity : Int,
    @SerializedName("cloud") val cloud : Int,
    @SerializedName("feelslike_c") val feelsLike_c : Double,
    @SerializedName("feelslike_f") val feelsLike_f : Double,
    @SerializedName("vis_km") val vis_km : Double,
    @SerializedName("vis_miles") val vis_miles : Int,
    @SerializedName("uv") val uv : Int
)
package com.abdullahalomair.businessfinder.model.wathermodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Current (
    @SerializedName("last_updated_epoch") val lastUpdatedEpoch : Int = 0,
    @SerializedName("last_updated") val lastUpdated : String = "",
    @SerializedName("temp_c") val tempC : Double = 0.0,
    @SerializedName("temp_f") val tempF : Double = 0.0,
    @SerializedName("is_day") val isDay : Int = 0,
    val condition : Condition = Condition(),
    @SerializedName("wind_mph") val windMph : Double = 0.0,
    @SerializedName("wind_kph") val windKph : Double = 0.0,
    @SerializedName("wind_degree") val windDegree : Int =0,
    @SerializedName("pressure_mb") val pressureMb : Int = 0,
    @SerializedName("pressure_in") val pressureIn : Double = 0.0,
    @SerializedName("humidity") val humidity : Int =0,
    @SerializedName("cloud") val cloud : Int =0,
    @SerializedName("feelslike_c") val feelsLike_c : Double= 0.0,
    @SerializedName("feelslike_f") val feelsLike_f : Double = 0.0,
    @SerializedName("vis_km") val vis_km : Double = 0.0,
    @SerializedName("vis_miles") val vis_miles : Int = 0,
    @SerializedName("uv") val uv : Int = 0
):Parcelable
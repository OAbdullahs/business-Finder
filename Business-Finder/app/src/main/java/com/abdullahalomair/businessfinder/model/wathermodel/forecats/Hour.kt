package com.abdullahalomair.businessfinder.model.wathermodel.forecats

import android.os.Parcelable
import androidx.room.Embedded
import com.abdullahalomair.businessfinder.model.wathermodel.Condition
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Hour (
    @SerializedName("time_epoch") val timeEpoch : Int = 0,
    @SerializedName("time") val time : String = "",
    @SerializedName("temp_c") val temp_c : Double = 0.0,
    @SerializedName("temp_f") val temp_f : Double =0.0,
    @SerializedName("is_day") val is_day : Int = 0,
    @SerializedName("condition") val condition : Condition = Condition(),
    @SerializedName("wind_mph") val wind_mph : Double = 0.0,
    @SerializedName("wind_kph") val wind_kph : Double = 0.0,
    @SerializedName("wind_degree") val wind_degree : Int = 0,
    @SerializedName("wind_dir") val wind_dir : String = "",
    @SerializedName("pressure_mb") val pressure_mb : Double = 0.0,
    @SerializedName("pressure_in") val pressure_in : Double = 0.0,
    @SerializedName("precip_mm") val precip_mm : Double = 0.0,
    @SerializedName("precip_in") val precip_in : Double = 0.0,
    @SerializedName("humidity") val humidity : Int = 0,
    @SerializedName("cloud") val cloud : Int = 0,
    @SerializedName("feelslike_c") val feelslike_c : Double=0.0,
    @SerializedName("feelslike_f") val feelslike_f : Double=0.0,
    @SerializedName("windchill_c") val windchill_c : Double=0.0,
    @SerializedName("windchill_f") val windchill_f : Double=0.0,
    @SerializedName("heatindex_c") val heatindex_c : Double=0.0,
    @SerializedName("heatindex_f") val heatindex_f : Double=0.0,
    @SerializedName("dewpoint_c") val dewpoint_c : Double=0.0,
    @SerializedName("dewpoint_f") val dewpoint_f : Double=0.0,
    @SerializedName("will_it_rain") val will_it_rain : Int = 0,
    @SerializedName("chance_of_rain") val chance_of_rain : Int = 0,
    @SerializedName("will_it_snow") val will_it_snow : Int = 0,
    @SerializedName("chance_of_snow") val chance_of_snow : Int = 0,
    @SerializedName("vis_km") val vis_km : Double = 0.0,
    @SerializedName("vis_miles") val vis_miles : Double = 0.0,
    @SerializedName("gust_mph") val gust_mph : Double=0.0,
    @SerializedName("gust_kph") val gust_kph : Double=0.0
):Parcelable
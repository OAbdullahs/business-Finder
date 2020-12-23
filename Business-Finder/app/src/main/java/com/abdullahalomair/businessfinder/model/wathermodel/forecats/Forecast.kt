package com.abdullahalomair.businessfinder.model.wathermodel.forecats

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast (
	@SerializedName("forecastday") val foreCastDay : List<ForeCastDay>
):Parcelable
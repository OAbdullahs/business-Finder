package com.abdullahalomair.businessfinder.model.wathermodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class WeatherModel (
	val location : Location = Location(),
	val current : Current = Current()
):Parcelable
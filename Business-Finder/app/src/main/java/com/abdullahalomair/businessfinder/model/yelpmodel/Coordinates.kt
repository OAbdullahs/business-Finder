package com.abdullahalomair.businessfinder.model.yelpmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinates (
	 val latitude : Double = 0.0,
	 val longitude : Double = 0.0
):Parcelable
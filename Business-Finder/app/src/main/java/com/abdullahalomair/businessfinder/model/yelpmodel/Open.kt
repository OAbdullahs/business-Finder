package com.abdullahalomair.businessfinder.model.yelpmodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Open (
	@SerializedName("is_overnight") val isOvernight : Boolean = false,
	val start : Int = 0,
 	val end : Int = 0,
 	val day : Int = 0
):Parcelable
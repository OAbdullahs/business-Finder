package com.abdullahalomair.businessfinder.model.yelpmodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Hours (
	val open : List<Open>,
	@SerializedName("hours_type") val hoursType : String,
	@SerializedName("is_open_now") val isOpenNow : Boolean
):Parcelable
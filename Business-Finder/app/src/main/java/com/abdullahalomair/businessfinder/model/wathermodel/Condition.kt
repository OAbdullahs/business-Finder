package com.abdullahalomair.businessfinder.model.wathermodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Condition (
	 val text : String = "",
	 val icon : String ="",
	 val code : Int = 0
):Parcelable
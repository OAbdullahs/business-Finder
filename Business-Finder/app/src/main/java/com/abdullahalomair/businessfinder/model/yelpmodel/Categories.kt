package com.abdullahalomair.businessfinder.model.yelpmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Categories (
	 val alias : String = "",
	 val title : String = ""
):Parcelable
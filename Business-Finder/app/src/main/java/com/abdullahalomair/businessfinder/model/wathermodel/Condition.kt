package com.abdullahalomair.businessfinder.model.wathermodel

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Condition (
	 val text : String = "",
	 val icon : String ="",
	 val code : Int = 0
):Parcelable
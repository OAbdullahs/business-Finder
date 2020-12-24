package com.abdullahalomair.businessfinder.model.wathermodel

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize

data class WeatherModel (
	 val location : Location = Location(),
	 val current : Current = Current()
):Parcelable
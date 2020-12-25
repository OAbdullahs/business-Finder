package com.abdullahalomair.businessfinder.model.wathermodel

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity
data class WeatherModel (
	@PrimaryKey val id:UUID = UUID.randomUUID(),
	val businessId:String = "",
	@Embedded val location : Location = Location(),
	@Embedded val current : Current = Current()
):Parcelable
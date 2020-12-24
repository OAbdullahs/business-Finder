package com.abdullahalomair.businessfinder.model.wathermodel.forecats

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Forecast (
	@SerializedName("forecastday") val foreCastDay : List<ForeCastDay>
):Parcelable
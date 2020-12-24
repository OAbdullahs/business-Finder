package com.abdullahalomair.businessfinder.model.wathermodel.forecats


import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.abdullahalomair.businessfinder.model.wathermodel.Current
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity
data class WeatherForeCast (
    @PrimaryKey val business_id: UUID = UUID.randomUUID(),
    @Embedded @SerializedName("location") val location : Location = Location(),
    @Embedded @SerializedName("current") val current : Current = Current(),
    @Embedded @SerializedName("forecast") val forecast : Forecast,
):Parcelable
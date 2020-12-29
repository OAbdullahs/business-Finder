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
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var businessId:String = "",
    @Embedded  val location : Location = Location(),
    @Embedded val current : Current = Current(),
    @Embedded  val forecast : Forecast = Forecast(),
):Parcelable
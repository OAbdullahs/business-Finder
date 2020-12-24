package com.abdullahalomair.businessfinder.model.yelpmodel

import android.location.Location
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity
data class BusinessDetails (
    @PrimaryKey val business_id: UUID = UUID.randomUUID(),
    val id : String = "",
    val alias : String = "",
    val name : String = "",
    @SerializedName("image_url") val imageUrl : String = "",
    @SerializedName("is_claimed") val isClaimed : Boolean = false,
    @SerializedName("is_closed") val isClosed : Boolean = false,
    @SerializedName("display_phone") val display_phone : String = "",
    @SerializedName("review_count") val reviewCount : Int = 0,
    @Embedded val categories : List<Categories> = emptyList(),
    val rating : Float = 0.0f,
    @Embedded val location : Location  = Location(""),
    @Embedded val coordinates : Coordinates = Coordinates(),
    val photos : List<String> = emptyList(),
    val price : String = "",
    @Embedded val hours : List<Hours> = emptyList(),
    val transactions : List<String> = emptyList()
):Parcelable
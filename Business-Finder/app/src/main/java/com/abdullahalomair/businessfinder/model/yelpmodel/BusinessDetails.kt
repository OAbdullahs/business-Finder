package com.abdullahalomair.businessfinder.model.yelpmodel

import android.location.Location
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BusinessDetails (
    val id : String = "",
    val alias : String = "",
    val name : String = "",
    @SerializedName("image_url") val imageUrl : String = "",
    @SerializedName("is_claimed") val isClaimed : Boolean = false,
    @SerializedName("is_closed") val isClosed : Boolean = false,
    @SerializedName("display_phone") val display_phone : String = "",
    @SerializedName("review_count") val reviewCount : Int = 0,
    val categories : List<Categories> = emptyList(),
    val rating : Float = 0.0f,
    val location : Location  = Location(""),
    val coordinates : Coordinates = Coordinates(),
    val photos : List<String> = emptyList(),
    val price : String = "",
    val hours : List<Hours> = emptyList(),
    val transactions : List<String> = emptyList()
):Parcelable
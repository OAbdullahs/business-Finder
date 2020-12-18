package com.abdullahalomair.businessfinder.model.yelpmodel

import android.location.Location
import com.google.gson.annotations.SerializedName




data class BusinessDetails (
    val id : String,
    val alias : String,
    val name : String,
    @SerializedName("image_url") val imageUrl : String,
    @SerializedName("is_claimed") val isClaimed : Boolean,
    @SerializedName("is_closed") val isClosed : Boolean,
    @SerializedName("display_phone") val display_phone : String,
    @SerializedName("review_count") val reviewCount : Int,
    val categories : List<Categories>,
    val rating : Float,
    val location : Location,
    val coordinates : Coordinates,
    val photos : List<String>,
    val price : String,
    val hours : List<Hours>,
    val transactions : List<String>
)
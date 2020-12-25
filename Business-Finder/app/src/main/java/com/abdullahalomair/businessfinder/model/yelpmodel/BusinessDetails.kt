package com.abdullahalomair.businessfinder.model.yelpmodel

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdullahalomair.businessfinder.model.wathermodel.Location
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

private const val PREFIX = "bus_"
@Parcelize
@Entity
data class BusinessDetails (
    @PrimaryKey val id_uuid:UUID = UUID.randomUUID(),
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
    @Embedded(prefix = PREFIX)val location : Location = Location(""),
    @Embedded(prefix = PREFIX)val coordinates : Coordinates = Coordinates(),
    val photos : List<String> = emptyList(),
    val price : String = "",
    val hours : List<Hours> = emptyList(),
):Parcelable
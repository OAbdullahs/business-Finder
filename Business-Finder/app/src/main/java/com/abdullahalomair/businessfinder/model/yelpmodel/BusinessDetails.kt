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
    @PrimaryKey var id_uuid:UUID = UUID.randomUUID(),
    var id : String = "",
    var alias : String = "",
    var name : String = "",
    @SerializedName("image_url")  var imageUrl : String = "",
    @SerializedName("is_claimed") var isClaimed : Boolean = false,
    @SerializedName("is_closed")  var isClosed : Boolean = false,
    @SerializedName("display_phone") var display_phone : String = "",
    @SerializedName("review_count") var reviewCount : Int = 0,
    var categories : MutableList<Categories> = mutableListOf(),
    var rating : Float = 0.0f,
    @Embedded(prefix = PREFIX)var location : Location = Location(""),
    @Embedded(prefix = PREFIX)var coordinates : Coordinates = Coordinates(),
    var photos : MutableList<String> = mutableListOf(),
    var price : String = "",
    var hours : MutableList<Hours> = mutableListOf(),
):Parcelable
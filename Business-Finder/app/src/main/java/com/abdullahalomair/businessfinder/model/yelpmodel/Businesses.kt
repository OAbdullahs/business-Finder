package com.abdullahalomair.businessfinder.model.yelpmodel




import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class Businesses (
	@PrimaryKey val business_id:UUID = UUID.randomUUID(),
	val id : String = "",
	val alias : String = "",
	val name : String = "",
	@SerializedName("image_url") val imageUrl : String = "",
	@SerializedName("is_closed") val isClosed : Boolean = false,
	@SerializedName("review_count") val reviewCount : Int = 0,
	@Embedded val categories : List<Categories> = emptyList(),
	val rating : Double = 0.0,
	@Embedded val coordinates : Coordinates = Coordinates(0.0,0.0),
	val price : String = "",
	val phone : String = "",
	@SerializedName("display_phone") val displayPhone : String = "",
	val distance : Double = 0.0
): Parcelable
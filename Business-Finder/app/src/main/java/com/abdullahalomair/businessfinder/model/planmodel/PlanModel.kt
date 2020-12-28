package com.abdullahalomair.businessfinder.model.planmodel

import androidx.annotation.ColorInt
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import java.util.*

private const val PLAN_DET= "plan_det"
private const val PLAN_BUS= "plan_bus"
private const val PLAN_WEATHER= "plan_weather_"
@Entity
data class PlanModel (
    @PrimaryKey val id_uuid:UUID = UUID.randomUUID(),
    @ColorInt var colorInt: Int = 0,
    var planTitle:String = "",
    @Embedded(prefix = PLAN_DET) var businessDetails: BusinessDetails = BusinessDetails(),
    @Embedded(prefix = PLAN_BUS) var businesses: Businesses = Businesses(),
    @Embedded(prefix = PLAN_WEATHER) var weatherForeCast: WeatherForeCast = WeatherForeCast(),
    var dueDate:Date = Date(),
    var completed:Boolean = false
)


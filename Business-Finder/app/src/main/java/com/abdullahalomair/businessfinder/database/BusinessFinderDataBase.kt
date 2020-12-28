package com.abdullahalomair.businessfinder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdullahalomair.businessfinder.model.planmodel.PlanModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList

@Database(entities = [WeatherForeCast::class,
    BusinessDetails::class,
    BusinessesList::class,
    PlanModel::class],version = 1)
@TypeConverters(BusinessFinderTypeConverter::class)
abstract class BusinessFinderDataBase: RoomDatabase() {
    abstract fun getBusinessFinderDao(): BusinessFinderDao
}
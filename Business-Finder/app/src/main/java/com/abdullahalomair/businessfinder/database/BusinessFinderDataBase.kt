package com.abdullahalomair.businessfinder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.Forecast
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList

@Database(entities = [WeatherModel::class,
    WeatherForeCast::class,
    BusinessDetails::class,
    BusinessesList::class],version = 1)
@TypeConverters(BusinessFinderTypeConverter::class)
abstract class BusinessFinderDataBase: RoomDatabase() {
    abstract fun getBusinessFinderDao(): BusinessFinderDao
}
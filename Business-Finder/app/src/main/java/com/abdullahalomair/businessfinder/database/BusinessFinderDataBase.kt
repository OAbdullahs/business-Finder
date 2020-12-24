package com.abdullahalomair.businessfinder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.Forecast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses

//@Database(entities = [WeatherModel::class,
//    BusinessDetails::class,
//    Businesses::class,
//    Forecast::class],version = 1)
//@TypeConverters(BusinessFinderTypeConverter::class)
//abstract class BusinessFinderDataBase: RoomDatabase() {
//    abstract fun getBusinessFinderDao(): BusinessFinderDao
//}
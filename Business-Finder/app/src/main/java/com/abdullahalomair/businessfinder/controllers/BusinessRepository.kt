package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.abdullahalomair.businessfinder.api.weatherapi.weatherfetcher.WeatherFetcher
import com.abdullahalomair.businessfinder.api.yelpapi.yelpfetcher.YelpFetcher
import com.abdullahalomair.businessfinder.database.BusinessFinderDataBase
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList
import java.util.concurrent.Executors

private const val DATABASE_NAME = "BusinessFinder-database"
class BusinessRepository private constructor(context: Context){
    private val database : BusinessFinderDataBase = Room.databaseBuilder(
        context.applicationContext,
        BusinessFinderDataBase::class.java,
        DATABASE_NAME
    ).build()
    private val executor = Executors.newSingleThreadExecutor()


    fun getBusinessList(location:String):LiveData<BusinessesList>{
        return YelpFetcher().getBusinessesByLocation(location)
    }
    suspend fun getBusinessDetail(businessId: String): BusinessDetails?{
        return YelpFetcher().getBusinessDetails(businessId)
    }
    suspend fun getWeatherData(location: String): WeatherModel?{
        return WeatherFetcher().getWeatherByLocation(location)
    }
    fun getWeatherForecast(location: String): LiveData<WeatherForeCast> {
        return WeatherFetcher().getWeatherForeCast(location)
    }

    companion object{
            private var INSTANCE: BusinessRepository? = null
            fun initialize(context: Context) {
                if (INSTANCE == null) {
                    INSTANCE = BusinessRepository(context)
                }
            }
            fun get(): BusinessRepository {
                return INSTANCE ?:
                throw IllegalStateException("BusinessRepository must be initialized")
            }

    }
}
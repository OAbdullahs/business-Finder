package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import androidx.lifecycle.LiveData
import com.abdullahalomair.businessfinder.api.weatherapi.weatherfetcher.WeatherFetcher
import com.abdullahalomair.businessfinder.api.yelpapi.yelpfetcher.YelpFetcher
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList

class BusinessRepository private constructor(context: Context){


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
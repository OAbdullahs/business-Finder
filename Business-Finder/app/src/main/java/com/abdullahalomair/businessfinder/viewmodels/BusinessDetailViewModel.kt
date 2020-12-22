package com.abdullahalomair.businessfinder.viewmodels

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.RawRes
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import com.abdullahalomair.businessfinder.controllers.BusinessRepository
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails

class BusinessDetailViewModel: BaseObservable() {

    private val repository = BusinessRepository.get()

    fun getWeatherForecast(location: String): LiveData<WeatherForeCast> {
        return repository.getWeatherForecast(location)
    }
    suspend fun getBusinessDetails(businessId:String): BusinessDetails? {
        return repository.getBusinessDetail(businessId)
    }

    var imageDrawable:Drawable? = null
        set(value) {
            field = value
            notifyChange()
        }
    var businessTitle: String? = null
         set(value) {
             field = value
             notifyChange()
         }
    var businessRatings: Float? = null
        set(value) {
            field = value
            notifyChange()
        }
    var businessAlias:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var isBusinessOpen: String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var priceTag: String? = null
        set(value) {
            field = value
            notifyChange()
        }

    var businessCityName:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var businessCountyName:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    @RawRes var currentWeatherIcon:Int? = null
        set(value) {
            field = value
            notifyChange()
        }
    var currentDate:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var currentWeather:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var averageWeather:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    @RawRes var secondDayWeatherIcon:Int? = null
        set(value) {
            field = value
            notifyChange()
        }
    var secondDayDate:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var secondDayWeatherDegree:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var secondDayWeatherAvg:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    @RawRes var thirdDayWeatherIcon:Int? = null
        set(value) {
            field = value
            notifyChange()
        }
    var thirdDayDate:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var thirdDayWeatherDegree:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var thirdDayWeatherAvg:String? = null
        set(value) {
            field = value
            notifyChange()
        }



}
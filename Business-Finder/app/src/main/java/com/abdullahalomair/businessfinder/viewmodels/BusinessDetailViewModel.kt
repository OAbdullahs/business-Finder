package com.abdullahalomair.businessfinder.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.RawRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.callbacks.CallBacks
import com.abdullahalomair.businessfinder.controllers.BusinessRepository
import com.abdullahalomair.businessfinder.model.navigator.Navigator
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.ForeCastDay
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails

class BusinessDetailViewModel(private val context: Context): BaseObservable() {

    private val repository = BusinessRepository.get()

    fun getWeatherForecast(location: String): LiveData<WeatherForeCast> {
        return repository.getWeatherForecast(location)
    }
    suspend fun getBusinessDetails(businessId:String): BusinessDetails? {
        return repository.getBusinessDetail(businessId)
    }
    private var callBacks: CallBacks? = null
    @ColorRes var textViewBackgroundColor: Int? = R.color.dark_gray
            set(value) {
                field = value
                notifyChange()
            }
    init {
        callBacks = context as CallBacks
        textViewBackgroundColor =  R.color.dark_gray
    }


    var weatherForeCastModel:List<ForeCastDay>? = null
    var cityName:String? = null


     fun goToCurrentWeatherDetail(){
        goToWeatherDetail(0)
    }

    fun goToSecondWeatherDetail(){
        goToWeatherDetail(1)

    }

    fun goToThirdWeatherDetail(){
        goToWeatherDetail(2)

    }

    private fun goToWeatherDetail(dayNumber:Int){
        if (cityName != null && weatherForeCastModel != null) {
            callBacks?.applicationNavigator(
                Navigator.WEATHER_DETAIL,
                cityName = cityName,
                weatherModel = weatherForeCastModel!![dayNumber]
            )
        }
        else{
            Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
        }
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
            textViewBackgroundColor = null
        }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        super.removeOnPropertyChangedCallback(callback)
        callBacks = null
    }

}
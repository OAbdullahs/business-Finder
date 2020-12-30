package com.abdullahalomair.businessfinder.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.RawRes
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.callbacks.CallBacks
import com.abdullahalomair.businessfinder.controllers.BusinessRepository
import com.abdullahalomair.businessfinder.controllers.TimeDetailBottomSheet
import com.abdullahalomair.businessfinder.model.navigator.Navigator
import com.abdullahalomair.businessfinder.model.planmodel.PlanModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.ForeCastDay
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

open class BusinessDetailViewModel(private val context: Context): BaseObservable() {


    private var businessDetails: BusinessDetails? = null
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

    private val repository = BusinessRepository.get()

    fun hasNetwork():Boolean{
        return repository.hasNetwork()
    }

    private fun insertPlanModel(planModel: PlanModel){
        repository.insertPlanDetail(planModel)
    }

    suspend fun getWeatherForecastLocal(id: String): WeatherForeCast? {
        return repository.getWeatherForecastLocal(id)
    }
    suspend fun getBusinessDetailsLocal(businessId:String): BusinessDetails? {
        return repository.getBusinessDetailLocal(businessId)
    }
    suspend fun getWeatherForecast(location: String, businessId: String): WeatherForeCast {
        val onlineRequest = repository.getWeatherForecast(location)
        if (onlineRequest != WeatherForeCast()){
            val localWeather = getWeatherForecastLocal(businessId)
            if (localWeather == null){
                onlineRequest.businessId = businessId
                insertWeatherForeCastLocal(onlineRequest)
            }
        }
        return onlineRequest
    }
    suspend fun getBusinessDetails(businessId:String): BusinessDetails {
        val onlineResponse = repository.getBusinessDetail(businessId)
        if (onlineResponse != BusinessDetails()){
        val localDetail = getBusinessDetailsLocal(businessId)
        if (localDetail == null){
            insertBusinessDetailsLocal(onlineResponse)
        }
        }
        return onlineResponse
    }
    private fun insertBusinessDetailsLocal(businessDetails: BusinessDetails)  =
        repository.insertBusinessDetailsLocal(businessDetails)
    private fun insertWeatherForeCastLocal(weatherForeCast: WeatherForeCast)  =
        repository.insertWeatherForeCastLocal(weatherForeCast)


    suspend fun addPlanToDataBase(business: Businesses, businessId: String, title:String){
        val businessDetails = getBusinessDetailsLocal(businessId) ?: BusinessDetails()
        val weatherForeCast = getWeatherForecastLocal(businessId) ?: WeatherForeCast()
        val planModel = PlanModel()
            planModel.businessDetails = businessDetails
            planModel.businesses = business
            planModel.planTitle = title
            planModel.weatherForeCast = weatherForeCast
            planModel.colorInt = randomColor!!
        try {
            planModel.dueDate = planDate!!
            insertPlanModel(planModel)
        }catch (e:NullPointerException){

        }


    }







    var weatherForeCastModel:List<ForeCastDay>? = null
    var cityName:String? = null





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

    var currentWeather:String? = null
        set(value) {
            field = value
            notifyChange()
            textViewBackgroundColor = null
        }
    var averageWeather:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    @RawRes var timeDetail:Int? = null
        set(value) {
            field = value
            notifyChange()
        }


     var randomColor:Int? = Color.argb(255,Random.nextInt(255),Random.nextInt(255),Random.nextInt(255))
         set(value) {
             field = value
             notifyChange()
         }

    var startDate:String? = SimpleDateFormat("EEE,dd hh:mm aa").format(Date())
        set(value) {
            field = value
            notifyChange()
        }

    var planDate:Date? = null
        set(value)  {
        startDate =SimpleDateFormat("EEE,dd hh:mm aa").format(value)
        field = value
    }





}
package com.abdullahalomair.businessfinder.api.weatherapi


import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "a115b83641e040bca7672540201612"
interface WeatherApi {

    @GET("current.json?key=$API_KEY")
    fun getCurrentWeatherData(@Query("q") location:String): Call<WeatherModel>
}
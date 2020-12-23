package com.abdullahalomair.businessfinder.api.weatherapi


import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "a115b83641e040bca7672540201612"
interface WeatherApi {

    @GET("current.json?key=$API_KEY")
    suspend fun getCurrentWeatherData(@Query("q") location:String): Response<WeatherModel>

    @GET("forecast.json?key=$API_KEY")
    fun getWeatherForecast(@Query("q") location:String,
                            @Query("days") days:Int = 3): Call<WeatherForeCast>
}
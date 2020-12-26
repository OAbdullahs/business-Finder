package com.abdullahalomair.businessfinder.api.weatherapi


import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "a115b83641e040bca7672540201612"
interface WeatherApi {
    @GET("forecast.json?key=$API_KEY")
    suspend fun getWeatherForecast(@Query("q") location:String,
                            @Query("days") days:Int = 3): Response<WeatherForeCast>
}
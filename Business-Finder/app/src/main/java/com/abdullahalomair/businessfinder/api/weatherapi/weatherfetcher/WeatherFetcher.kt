package com.abdullahalomair.businessfinder.api.weatherapi.weatherfetcher


import com.abdullahalomair.businessfinder.api.weatherapi.WeatherApi
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class WeatherFetcher {
    private val weatherApi: WeatherApi
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApi = retrofit.create(WeatherApi::class.java)

    }

    suspend fun  getWeatherForeCast (location:String): WeatherForeCast {
        return try {
            weatherApi.getWeatherForecast(location).body() ?: WeatherForeCast()
        }catch (e:IOException){
            WeatherForeCast()
        }
    }
}
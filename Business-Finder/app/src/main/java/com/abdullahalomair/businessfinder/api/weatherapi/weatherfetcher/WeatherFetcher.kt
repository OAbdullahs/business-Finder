package com.abdullahalomair.businessfinder.api.weatherapi.weatherfetcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdullahalomair.businessfinder.api.weatherapi.WeatherApi
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherFetcher {
    private val weatherApi: WeatherApi
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApi = retrofit.create(WeatherApi::class.java)

    }

    suspend fun  getWeatherByLocation(location:String): WeatherModel? {
        return weatherApi.getCurrentWeatherData(location).body()
    }
}
package com.abdullahalomair.businessfinder.api.weatherapi.weatherfetcher

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdullahalomair.businessfinder.api.weatherapi.WeatherApi
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList
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
    fun  getWeatherForeCast (location:String): LiveData<WeatherForeCast> {
        val call: Call<WeatherForeCast> = weatherApi.getWeatherForecast(location)
        val responseLiveData: MutableLiveData<WeatherForeCast> = MutableLiveData()
        call.enqueue(object : Callback<WeatherForeCast>{
            override fun onResponse(
                call: Call<WeatherForeCast>,
                response: Response<WeatherForeCast>
            ) {
                responseLiveData.value =  response.body()
            }

            override fun onFailure(call: Call<WeatherForeCast>, t: Throwable) {

                throw t
            }
        })
        return responseLiveData
    }
}
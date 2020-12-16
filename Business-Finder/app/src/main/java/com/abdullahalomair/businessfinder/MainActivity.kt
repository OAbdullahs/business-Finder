package com.abdullahalomair.businessfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.abdullahalomair.businessfinder.api.weatherapi.weatherfetcher.WeatherFetcher
import com.abdullahalomair.businessfinder.api.yelpapi.yelpfetcher.YelpFetcher
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
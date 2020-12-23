package com.abdullahalomair.businessfinder.controllers

import android.util.Log
import androidx.annotation.RawRes
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.databinding.WeatherTimeDesignBinding
import com.abdullahalomair.businessfinder.getWeatherAnimationName
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.Hour
import com.abdullahalomair.businessfinder.utils.toDp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val C_DEGREE = "℃"
class WeatherHolder(private val binding: WeatherTimeDesignBinding)
    :RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherData: Hour){

            val (_, time) = weatherData.time.split(" ")

            try {
                val sdf = SimpleDateFormat("HH:mm")
                val dateObj = sdf.parse(time)
                val finalTime = SimpleDateFormat("hh aa").format(dateObj)
                binding.timeTextView.text = finalTime
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            @RawRes val getWeatherImage =
                getWeatherAnimationName(weatherData.condition.code)
            binding.weatherAnimatin.setAnimation(getWeatherImage)
            binding.weatherTemp.text = "${weatherData.temp_c}$C_DEGREE"
        }
}
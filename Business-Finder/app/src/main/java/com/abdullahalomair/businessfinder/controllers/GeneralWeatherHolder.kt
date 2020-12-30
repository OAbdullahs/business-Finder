package com.abdullahalomair.businessfinder.controllers

import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.callbacks.CallBacks
import com.abdullahalomair.businessfinder.databinding.WeatherGeneralListBinding
import com.abdullahalomair.businessfinder.getWeatherAnimationName
import com.abdullahalomair.businessfinder.model.navigator.Navigator
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.ForeCastDay

private const val UPPER_ARROW = "↑"
private const val DOWN_ARROW  = "↓"
private const val C_DEGREE    = "℃"
class GeneralWeatherHolder(private val binding: WeatherGeneralListBinding)
    :RecyclerView.ViewHolder(binding.root) {
    private var callBacks: CallBacks? = null

    init {
        callBacks = itemView.context as CallBacks
    }

        fun binding(weather: ForeCastDay, cityName: String){
            val maxDegree = weather.day.maxTemp_c
            val minDegree = weather.day.minTemp_c
            val finalTextAvg =
                "$maxDegree$C_DEGREE$UPPER_ARROW $minDegree$C_DEGREE$DOWN_ARROW"
            binding.averageWeather.text = finalTextAvg
            binding.currentDate.text = weather.date
            val weatherIconRawRes =
                getWeatherAnimationName(weather.day.condition.code)
            binding.lottieAnimation.setAnimation(weatherIconRawRes)
            binding.currentDayWeather.text = "${weather.day.avgTemp_c}$C_DEGREE"
            itemView.setOnClickListener {
                callBacks?.applicationNavigator(
                    Navigator.WEATHER_DETAIL,
                    cityName = cityName,
                    weatherModel = weather
                )
            }

        }
}
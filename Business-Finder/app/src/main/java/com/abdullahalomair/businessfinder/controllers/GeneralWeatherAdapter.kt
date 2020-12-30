package com.abdullahalomair.businessfinder.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.WeatherGeneralListBinding
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.ForeCastDay

class GeneralWeatherAdapter(private val weather: List<ForeCastDay>,
                            private val cityName:String):
    RecyclerView.Adapter<GeneralWeatherHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralWeatherHolder {
        val binding: WeatherGeneralListBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.weather_general_list, parent, false
            )
        return GeneralWeatherHolder(binding)
    }

    override fun onBindViewHolder(holder: GeneralWeatherHolder, position: Int) {
        holder.binding(weather[position],cityName)
    }

    override fun getItemCount(): Int = weather.size
}
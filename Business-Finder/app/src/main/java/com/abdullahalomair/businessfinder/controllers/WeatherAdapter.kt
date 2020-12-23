package com.abdullahalomair.businessfinder.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.WeatherTimeDesignBinding
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.ForeCastDay
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.Hour

class WeatherAdapter(private val weatherCast: List<Hour>)
    : RecyclerView.Adapter<WeatherHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val binding: WeatherTimeDesignBinding
        = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.weather_time_design,
            parent,
            false
        )
        return WeatherHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.bind(weatherCast[position])
    }

    override fun getItemCount(): Int = weatherCast.size
}
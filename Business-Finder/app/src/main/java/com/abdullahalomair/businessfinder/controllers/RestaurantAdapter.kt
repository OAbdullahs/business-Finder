package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.TopRatingBusinessBinding
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import kotlin.reflect.KSuspendFunction1

class RestaurantAdapter(private val context: Context,
                        private val businesses: List<Businesses>,
                        private val getWeatherDetail: KSuspendFunction1<String, WeatherModel?>,
                        private val getBusinessDetail: KSuspendFunction1<String, BusinessDetails?>
                        )
    :RecyclerView.Adapter<RestaurantHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val binding: TopRatingBusinessBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.top_rating_business,
                parent,
                false
        )
        return RestaurantHolder(context,binding,getWeatherDetail,getBusinessDetail)
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        holder.bind(businesses[position])
    }

    override fun getItemCount(): Int = businesses.size

}
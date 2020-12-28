package com.abdullahalomair.businessfinder.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.BusinessRecyclerviewBinding
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import kotlinx.coroutines.*

class RestaurantAdapter(private val businesses: List<Businesses>,
                        )
    :RecyclerView.Adapter<RestaurantHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val binding: BusinessRecyclerviewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.business_recyclerview,
                parent,
                false
        )
        return RestaurantHolder(binding)
    }
    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
                holder.bind(businesses[position])
        }
    override fun getItemCount(): Int = businesses.size

}
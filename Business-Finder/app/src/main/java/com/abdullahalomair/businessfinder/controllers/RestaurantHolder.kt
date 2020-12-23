package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.recyclerview.widget.*
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.callbacks.CallBacks
import com.abdullahalomair.businessfinder.databinding.AllBusinessBinding
import com.abdullahalomair.businessfinder.model.navigator.Navigator
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import java.io.IOException


class RestaurantHolder(
    private val binding: AllBusinessBinding,
) : RecyclerView.ViewHolder(binding.root) {
        private var callback: CallBacks? = null
    init {
       callback =  itemView.context as CallBacks
        binding.apply {
            businessName.startShimmer()
            businessType.startShimmer()
        }
    }
    fun bind(allData: Businesses, businessDetails: BusinessDetails, weatherModel: WeatherModel) {
        Glide.with(itemView)
            .load(allData.imageUrl)
            .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
            .into(binding.restaurantMainImage)
        try {
            businessDetail(businessDetails)
        } catch (e: IOException) {
            Toast.makeText(itemView.context, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
        try {
            displayWeatherData(weatherModel)
        } catch (e: Exception) {


        }

        itemView.setOnClickListener {
                callback?.applicationNavigator(Navigator.BUSINESS_DETAILS,allData,businessDetails)


        }

        binding.apply {
            businessNameText.apply{
                text = allData.name
                background = null
            }
            businessTypeText.apply{
                text = allData.categories.last().title
                background = null
            }
            ratingBar.rating = allData.rating.toFloat()
            businessName.hideShimmer()
            businessType.hideShimmer()

        }


    }

    private fun displayWeatherData(weatherModel: WeatherModel) {
        binding.apply {
            val weatherIcon  = "https://"+weatherModel.current.condition.icon
            Glide.with(itemView)
                .load(weatherIcon)
                .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
                .into(binding.weatherImage)
            val text = "${weatherModel.current.tempC}${0x00B0.toChar()}C"
            weatherValue.text = text


        }
    }

    private fun businessDetail(businessData: BusinessDetails) {
        try {
        val isBusinessClosed = if (!businessData.hours.last().isOpenNow) {
            itemView.context.getString(R.string.business_closed)
        } else {
            itemView.context.getString(R.string.business_open)
        }

        binding.isOpenText.apply {
            val finalText = if (businessData.price.isEmpty()) {
                isBusinessClosed
            } else {
                "$isBusinessClosed . ${businessData.price}"
            }
            text = finalText
            background = null
        }
        binding.isBusinessOpen.hideShimmer()

        Glide.with(itemView)
            .load(businessData.photos[0])
            .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
            .into(binding.imageDetail1)

        Glide.with(itemView)
            .load(businessData.photos[1])
            .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
            .into(binding.imageDetail2)

        Glide.with(itemView)
            .load(businessData.photos[2])
            .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
            .into(binding.imageDetail3)
        }catch (e: NoSuchElementException){
        }

    }




}



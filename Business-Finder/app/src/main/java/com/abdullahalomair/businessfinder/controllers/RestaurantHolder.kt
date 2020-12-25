package com.abdullahalomair.businessfinder.controllers


import android.widget.Toast
import androidx.recyclerview.widget.*
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.callbacks.CallBacks

import com.abdullahalomair.businessfinder.databinding.BusinessRecyclerviewBinding
import com.abdullahalomair.businessfinder.model.navigator.Navigator
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.io.IOException
import java.lang.IndexOutOfBoundsException


class RestaurantHolder(
    private val binding: BusinessRecyclerviewBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var callback: CallBacks? = null

    init {
        callback = itemView.context as CallBacks

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
            callback?.applicationNavigator(
                Navigator.BUSINESS_DETAILS,
                businesses = allData,
                businessDetails = businessDetails
            )


        }

        binding.apply {
            businessNameText.apply {
                text = allData.name
                background = null
            }
            businessTypeText.apply {
                text = allData.categories.last().title
                background = null
            }
            ratingBar.rating = allData.rating.toFloat()


        }


    }

    private fun displayWeatherData(weatherModel: WeatherModel) {
        binding.apply {
            val weatherIcon = "https://" + weatherModel.current.condition.icon
            Glide.with(itemView)
                .load(weatherIcon)
                .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.weatherImage)
            val currentWeather = "${weatherModel.current.tempC}${0x00B0.toChar()}C"
            weatherValue.apply {
                text = currentWeather
                background = null
            }


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
            binding.businessShimmerFragment.hideShimmer()

            try {

                Glide.with(itemView)
                    .load(businessData.photos[0])
                    .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.imageDetail1)
            } catch (e: IndexOutOfBoundsException) {

            }
            try {

                Glide.with(itemView)
                    .load(businessData.photos[1])
                    .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.imageDetail2)
            } catch (e: IndexOutOfBoundsException) {

            }

            try {

                Glide.with(itemView)
                    .load(businessData.photos[2])
                    .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.imageDetail3)
            } catch (e: IndexOutOfBoundsException) {

            }
        } catch (e: NoSuchElementException) {
        }

    }


}



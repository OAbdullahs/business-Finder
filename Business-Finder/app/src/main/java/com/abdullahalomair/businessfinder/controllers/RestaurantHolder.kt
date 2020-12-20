package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.recyclerview.widget.*
import com.abdullahalomair.businessfinder.BR
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.TopRatingBusinessBinding
import com.abdullahalomair.businessfinder.getWeatherAnimationName
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.abdullahalomair.businessfinder.viewmodels.MainFragmentViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import java.io.IOException
import kotlin.reflect.KSuspendFunction1


class RestaurantHolder(private val context: Context,
                       private val binding: TopRatingBusinessBinding,
                       private val getWeatherDetail: KSuspendFunction1<String, WeatherModel?>,
                       private val getBusinessDetail: KSuspendFunction1<String, BusinessDetails?>
                       )
    : RecyclerView.ViewHolder(binding.root) {
    private lateinit var imagesAdapter: ImagesAdapter
    private val scope = CoroutineScope(Dispatchers.IO)
    init {
        binding.restaurantImagesRecyclerview.layoutManager =
            StaggeredGridLayoutManager(3,LinearLayoutManager.HORIZONTAL)
        val scrollHelper = LinearSnapHelper()
        scrollHelper.attachToRecyclerView(binding.restaurantImagesRecyclerview)
    }

        fun bind(allData: Businesses){
            Glide.with(itemView)
                .load(allData.imageUrl)
                .into(binding.restaurantMainImage)
            try {
                displayPhotos(allData)
            }catch (e:IOException){
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            try {
                displayWeatherData("${allData.coordinates.latitude},${allData.coordinates.longitude}")
            }
            catch (e:IOException){
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            val isBusinessClosed = if (allData.isClosed)
            {
                context.getString(R.string.business_closed)
            }
            else {
                context.getString(R.string.business_open)
            }

            binding.apply {
                businessName.text = allData.name
                businessType.text = allData.categories.last().title
                isOpenText.text = isBusinessClosed
                ratingBar.rating = allData.rating.toFloat()

            }
        }
        private  fun displayWeatherData(location:String) {
           val isCompleted =  scope.launch {
                val weather = getWeatherDetail(location)
               withContext(Dispatchers.Main){
                   if (weather != null){
                       binding.apply {
                           @RawRes val weatherStatus = getWeatherAnimationName(weather.current.condition.code)
                           weatherAnimation.setAnimation(weatherStatus)
                           weatherValue.text = weather.current.tempC.toString()

                       }
                   }
               }
               }.isCompleted

            if (isCompleted){
                scope.cancel()
            }
        }

        private fun displayPhotos(allData: Businesses) {
           val isComplete =  scope.launch {
               val images =  getBusinessDetail(allData.id)
                withContext(Dispatchers.Main){
                    if (images != null) {
                        binding.restaurantImagesRecyclerview.apply {
                            imagesAdapter = ImagesAdapter(context, images.photos)
                            adapter = imagesAdapter
                        }
                    }
                }
            }.isCompleted
            if (isComplete){
                scope.cancel()
            }


        }
}
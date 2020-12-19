package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.*
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.TopRatingBusinessBinding
import com.abdullahalomair.businessfinder.getWeatherAnimationName
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.abdullahalomair.businessfinder.viewmodels.TopRatingBusinesses
import com.bumptech.glide.Glide


class RestaurantHolder(private val activity: MainActivity,
                       private val context: Context,
                       private val binding: TopRatingBusinessBinding)
    : RecyclerView.ViewHolder(binding.root) {
    private lateinit var imagesAdapter: ImagesAdapter
    init {
        binding.viewModel = TopRatingBusinesses()
        binding.restaurantImagesRecyclerview.layoutManager =
            StaggeredGridLayoutManager(3,LinearLayoutManager.HORIZONTAL)
        val scrollHelper = LinearSnapHelper()
        scrollHelper.attachToRecyclerView(binding.restaurantImagesRecyclerview)
    }

        fun bind(allData: Businesses){
            Glide.with(itemView)
                .load(allData.imageUrl)
                .into(binding.restaurantMainImage)
            displayPhotos(allData)
            displayWeatherData("${allData.coordinates.latitude},${allData.coordinates.longitude}")
            val isBusinessClosed = if (allData.isClosed)
            {
                context.getString(R.string.business_closed)
            }
            else {
                context.getString(R.string.business_open)
            }

            binding.apply {
                viewModel?.businessName = allData.name
                viewModel?.businessType = allData.categories.last().title
                viewModel?.isBusinessOpen = isBusinessClosed
                viewModel?.ratingBusiness = allData.rating.toFloat()
            }
        }
        private fun displayWeatherData(location:String){
            binding.viewModel?.getWeatherDetails(location)?.observeForever { data ->
                binding.apply {
                    val weatherStatus = getWeatherAnimationName(data.current.condition.code)
                    viewModel?.weatherStatusAnimation = "@raw/w_sunny"
                    viewModel?.weatherValue = data.current.tempC.toString()
                }

            }
        }

        private fun displayPhotos(allData: Businesses){
            try {
                binding.viewModel?.getBusinessDetail(allData.id)?.observeForever { data ->
                    binding.restaurantImagesRecyclerview.apply {
                        imagesAdapter = ImagesAdapter(activity, context, data.photos)
                        adapter = imagesAdapter

                    }
                }
            }catch (e:NullPointerException){
                try {
                binding.viewModel?.getBusinessDetail(allData.id)?.observeForever { data ->
                    binding.restaurantImagesRecyclerview.apply {
                        imagesAdapter = ImagesAdapter(activity, context, data.photos)
                        adapter = imagesAdapter

                    }
                }
            }catch (e:NullPointerException){}

            }
        }
}
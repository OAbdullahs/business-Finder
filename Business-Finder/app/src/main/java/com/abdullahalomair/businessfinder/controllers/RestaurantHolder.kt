package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.TopRatingBusinessBinding
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.abdullahalomair.businessfinder.viewmodels.TopRatingBusinesses


class RestaurantHolder(private val activity: MainActivity,
                       private val context: Context,
                       private val binding: TopRatingBusinessBinding)
    : RecyclerView.ViewHolder(binding.root) {
    private lateinit var imagesAdapter: ImagesAdapter
    init {
        binding.viewModel = TopRatingBusinesses()
        binding.restaurantImagesRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        val scrollHelper = LinearSnapHelper()
        scrollHelper.attachToRecyclerView(binding.restaurantImagesRecyclerview)
    }

        fun bind(allData: Businesses){
            displayPhotos(allData)
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
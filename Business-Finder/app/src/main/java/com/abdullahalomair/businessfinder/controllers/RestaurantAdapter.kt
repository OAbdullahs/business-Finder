package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList

class RestaurantAdapter(private val context: Context,
                        private val businesses: BusinessesList)
    :RecyclerView.Adapter<RestaurantHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = businesses.total
}
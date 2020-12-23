package com.abdullahalomair.businessfinder.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.BusinessRecyclerviewBinding
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import kotlinx.coroutines.*
import kotlin.reflect.KSuspendFunction1

class RestaurantAdapter(private val businesses: List<Businesses>,
                        private val getWeatherDetail: KSuspendFunction1<String, WeatherModel>,
                        private val getBusinessDetail: KSuspendFunction1<String, BusinessDetails>,
                        )
    :RecyclerView.Adapter<RestaurantHolder>() {
    private val scope = CoroutineScope(Dispatchers.Default)

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
        scope.launch {
            val latAndLng = "${businesses[position].coordinates.latitude},${businesses[position].coordinates.longitude}"
        val businessDetail = getBusinessDetail(businesses[position].id)
        val weatherDetail = getWeatherDetail(latAndLng)
            withContext(Dispatchers.Main){
                holder.bind(businesses[position],businessDetail,weatherDetail)
            }
        }

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        scope.cancel()
    }

    override fun getItemCount(): Int = businesses.size

}